(ns chap-10)

(def fred (atom {:cuddle-hunger-level 0
                 :percent-derteriorated 0}))
@fred ; {:cuddle-hunger-level 0, :percent-derteriorated 0}

(let [zombie-state @fred]
  (when (>= (:percent-derteriorated zombie-state) 50)
    (future
      (println (:percent-derteriorated zombie-state)))))

(swap! fred
       (fn [current-state]
         (merge-with + current-state {:cuddle-hunger-level 1})))
@fred

(swap! fred
       (fn [current-state]
         (merge-with + current-state {:cuddle-hunger-level 1
                                      :percent-derteriorated 1})))
@fred ; {:cuddle-hunger-level 2, :percent-derteriorated 1}

(defn increase-cuddle-hunger-level [zombie-state increase-by]
  (merge-with + zombie-state {:cuddle-hunger-level increase-by}))

(increase-cuddle-hunger-level @fred 10)
; {:cuddle-hunger-level 12, :percent-derteriorated 1}

(swap! fred increase-cuddle-hunger-level 10)
@fred
; {:cuddle-hunger-level 12, :percent-derteriorated 1}

(update-in {:a {:b 3}} [:a :b] inc)
;; {:a {:b 4}}
(update-in {:a {:b 3}} [:a :b] + 10)
;; {:a {:b 13}}
(swap! fred update-in [:cuddle-hunger-level] + 10)
;; {:cuddle-hunger-level 22, :percent-derteriorated 1}

(swap! fred update-in [:percent-derteriorated] + 50)
@fred

(let [num (atom 1)
      s1 @num]
  (swap! num inc)
  (println "State 1:" s1)
  (println "Current state:" @num))
;; State 1: 1
;; Current state: 2

;; swap
;; 관찰과 검증자

(defn shuffle-speed [zombie]
  (* (:cuddle-hunger-level zombie)
     (- 100 (:percent-derteriorated zombie))))

(defn shuffle-alert [key watched old-state new-state]
  (let [sph (shuffle-speed new-state)]
    (if (> sph 5000)
      (do
        (prn "Run, you fool")
        (prn "zombie's SPH " sph))
      (do
        (prn "All's well with " key)
        (prn "SPH:" sph)))))


(reset! fred {:cuddle-hunger-level 22
              :percent-derteriorated 2})
(add-watch fred :fred-shuffle-alert shuffle-alert)
(swap! fred update-in [:percent-derteriorated] + 1)

(swap! fred update-in [:cuddle-hunger-level] + 30)

;; (defn percent-deteriorated-validator [{:keys [percent-deteriorated]}]
;;   (and (>= percent-deteriorated 0)
;;        (<= percent-deteriorated 100)))

(defn percent-deteriorated-validator [{:keys [percent-deteriorated]}]
  (or (and (>= percent-deteriorated 0)
           (<= percent-deteriorated 100))
      (throw (IllegalStateException. "That's not mathy!"))))


(def bobby
  (atom {:cuddle-hunger-level 0
         :percent-deteriorated 0}
        :validator percent-deteriorated-validator))

(swap! bobby update-in [:percent-deteriorated] + 200)

@bobby

;; 참조


(def sock-varieties
  #{"darned", "argyle", "wool",
    "horsehair", "mulleted", "passive-aggressive",
    "striped", "polka-dotted", "athletic",
    "business", "power", "invisible",
    "gollumed"})

(defn sock-count [sock-variety count]
  {:variety sock-variety
   :count count})

(defn generate-sock-gnome
  "Create an initial sock gnome state with no socks"
  [name]
  {:name name
   :socks #{}})

(def sock-gnome
  (ref (generate-sock-gnome "Barumpharumph")))

(def dryer
  (ref {:name "LG 1337"
        :socks (set (map #(sock-count % 2) sock-varieties))}))

(:socks @dryer)
;; `ref` 를 사용한 이유는?? (참조)
;; 참조 값은 `alter`를 써서 바꾼다.

(defn steal-sock [gnome dryer]
  (dosync
   (when-let [pair (some #(when (= (:count %) 2) %) (:socks @dryer))]
     (let [updated-count (sock-count (:variety pair) 1)]
       (alter gnome update-in [:socks] conj updated-count)
       (alter dryer update-in [:socks] disj pair)
       (alter dryer update-in [:socks] conj updated-count)))))

(steal-sock sock-gnome dryer)

(:socks @dryer)
(:socks @sock-gnome)

(defn similar-socks
  [target-sock sock-set]
  (filter (fn [sock] (= (:variety sock)
                        (:variety target-sock)))
          sock-set))

(similar-socks (first (:socks @sock-gnome)) (:socks @dryer))

(def counter (ref 0))

(future
  (dosync (alter counter inc)
          (println @counter)
          (Thread/sleep 500)
          (alter counter inc)
          (println @counter)))
(Thread/sleep 250)
(println @counter)

(defn sleep-print-update [sleep-time thread-name update-fn]
  (fn [state]
    (Thread/sleep sleep-time)
    (println (str thread-name ": " state))
    (update-fn state)))

(def counter (ref 0))

(future (dosync (commute counter (sleep-print-update 100 "Thread A" inc))))
(future (dosync (commute counter (sleep-print-update 150 "Thread B" inc))))

@counter

;; 안전하지 않은 사용 예
(def receiver-a (ref #{}))
(def receiver-b (ref #{}))
(def giver (ref #{1}))

(do (future (dosync (let [gift (first @giver)]
                      (Thread/sleep 10)
                      (commute receiver-a conj gift)
                      (commute giver disj gift))))
    (future (dosync (let [gift (first @giver)]
                      (Thread/sleep 50)
                      (commute receiver-b conj gift)
                      (commute giver disj gift)))))

@receiver-a
@receiver-b
@giver


