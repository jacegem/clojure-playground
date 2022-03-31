(ns chap-3)

;; https://www.braveclojure.com/do-things/

(defn inc-maker
  "Create a custom incrementor"
  [inc-by]
  #(+ % inc-by))

(def inc3 (inc-maker 3))

(inc3 7)

(defn dec-maker
  [dec-by]
  #(- % dec-by))

(def dec9 (dec-maker 9))

(dec9 10)

(defn dec-maker
  [dec-by]
  #(- % dec-by))

(defn mapset [f coll]
  (set
   (map f coll)))

(mapset inc [1 1 2 2])


(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "eye" :size 1 :count 2}
                             {:name "ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "shoulder" :size 3}
                             {:name "upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "forearm" :size 3 :count 2}
                             {:name "abdomen" :size 6}
                             {:name "kidney" :size 1}
                             {:name "hand" :size 2}
                             {:name "knee" :size 2}
                             {:name "thigh" :size 4}
                             {:name "leg" :size 3 :count 2}
                             {:name "achilles" :size 1}
                             {:name "foot" :size 2}])

(defn make-body-parts [{:keys [total-body-parts] :as state} body-part]
  (if-let [count (:count body-part)]
    (let [result (map (fn [idx]
                        (let [name (:name body-part)
                              new-name (str name "-" idx)
                              new-part (assoc body-part :name new-name)]
                          new-part)) (range count))
          new-total (apply conj total-body-parts result)]
      (assoc state :total-body-parts new-total))
    (let [result (conj total-body-parts body-part)]
      (assoc state :total-body-parts result))))

(make-body-parts {:total-body-parts []} {:name "leg" :size 3 :count 2})


(reduce make-body-parts {:total-body-parts []} asym-hobbit-body-parts)


(into {} {:name "leg" :size 3 :count 2})
(let [total-body-parts {}
      body-part  {:name "leg" :size 3 :count 2}
      count 2]
  (map (fn [idx]
         (let [name (:name body-part)
               new-name (str name "-" idx)
               new-part (assoc body-part :name new-name)]
           (into total-body-parts new-part))) (range count)))

;;([[:name "leg-0"] [:size 3] [:count 2]] [[:name "leg-1"] [:size 3] [:count 2]])

(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

(defn symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (loop [remaining-asym-parts asym-body-parts
         final-body-parts []]
    (if (empty? remaining-asym-parts)
      final-body-parts
      (let [[part & remaining] remaining-asym-parts]
        (recur remaining
               (into final-body-parts
                     (set [part (matching-part part)])))))))


(float 1/5)

(+ 1 (bit-not 10))


(defn dec-maker
  [dec-by]
  #(+ % 1 (bit-not dec-by)))

(def dec9 (dec-maker 9))

(dec9 10)