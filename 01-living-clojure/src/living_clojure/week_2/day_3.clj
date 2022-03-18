(ns living-clojure.week-2.day-3)


;; 간단한 클로저
(defn simple [x n]
  (-> (Math/pow n x)
      int))

(simple 2 3)

(defn part [n]
  (partial simple n))

(= 256 ((part 2) 16) ((part 8) 2))


;; 데카르트 곱
(defn cp [a b]
  (set (for [ea a
             eb b]
         [ea eb])))

(cp #{1 2 3} #{4 5})

(= 300 (count (cp (into #{} (range 10))
                  (into #{} (range 30)))))





