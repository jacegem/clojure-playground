(ns living-clojure.week-2.day-4)


;; 대칭 차집합
;; (= (__ #{1 2 3 4 5 6} #{1 3 5 7}) #{2 4 6 8})

(remove #{1 2 3 4 5 6} #{1 3 5 7})
(remove #{1 3 5 7} #{1 2 3 4 5 6})

(defn diff [a b]
  (let [da (remove a b)
        db (remove b a)]
    (-> (conj da db)
        flatten
        set)))

(diff #{1 2 3 4 5 6} #{1 3 5 7})
(= (diff #{1 2 3 4 5 6} #{1 3 5 7}) #{2 4 6 7})

;; 최소 공배수
(defn gcd [a b]
  (if (zero? b)
    a
    (recur b, (mod a b))))

(defn lcm [a b]
  (/ (* a b) (gcd a b)))

(defn lcm-m [& args]
  (->> (reduce gcd (first args) (rest args))
       (/ (apply * args))))

(== (lcm 2 3) 6)
(== (lcm-m 5 3 7) 105)



