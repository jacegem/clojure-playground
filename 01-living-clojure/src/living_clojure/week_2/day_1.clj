(ns living-clojure.week-2.day-1)

;; 피보나치  수열
;; (= (__ 3) '(1 1 2))

(defn fib [{:keys [result a b] :as state}]
  (let [next (+ a b)]
    (-> state
        (assoc :result (conj result next))
        (assoc :a b)
        (assoc :b next))))

(defn fib-n [n]
  (let [result (->> (iterate fib {:result '(1 1) :a 1 :b 1})
                    (filter #(= n (count (:result %))))
                    first)]
    (reverse (:result result))))

(fib-n 8)
;;=> (1 1 2 3 5 8 13 21)


;; 대문자 구하기
;; (= (__ "HeLlO, WoRlD!") "HLOWRD")

(defn only-uppercase [s]
  (apply str (re-seq #"[A-Z]" s)))

(only-uppercase "HeLlO, WoRlD!")
;;=> "HLOWRD"


;; some 소개
;; (= (some #{2 7 6} [5 6 7 8]))

(some #{2 7 6} [5 6 7 8])
;;=> 6


;; 팩토리얼 놀이
;; (= (__ 1) 1)
;; (= (__ 3) 6)
(defn fac [{:keys [current result] :as state}]
  (-> state
      (assoc :current (inc current))
      (assoc :result (* result current))))

(defn fac-n [n]
  (let [result (->> (iterate fac {:current 1 :result 1})
                    (filter #(< n (:current %)))
                    first)]
    (:result result)))

(fac-n 5)


;; 구조분해 소개
(= [2 4]
   #_{:clj-kondo/ignore [:unused-binding]}
   (let [[a b c d e] [0 1 2 3 4]]
     [c e]))

