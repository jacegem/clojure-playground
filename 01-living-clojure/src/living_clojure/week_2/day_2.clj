(ns living-clojure.week-2.day-2)


;; 구조분해 고급
#_{:clj-kondo/ignore [:unused-binding]}
(let [[a b & c] ["cat", "dog", "bird", "fish"]]
  [a b])
;;=> ["cat" "dog"]

#_{:clj-kondo/ignore [:unused-binding]}
(let [[a b & c] ["cat", "dog", "bird", "fish"]]
  c)
;;=> ("bird" "fish")

(= [1 2 [3 4 5] [1 2 3 4 5]]
   (let [[a b & c :as d] [1 2 3 4 5]]
     [a b c d]))


;; 반쪽 진실
;; (= false (__ false false))

(defn half-truth [& args]
  (if-let [result (some #(= false %) args)]
    result
    false))

(half-truth true true)


;; 최대 공약수
;; def gcd(a: int, b: int):
;;     a, b = max(a, b), min(a, b)
;;     while b:
;;         a, b = b, a % b
;;     return a

(defn gcd [a b]
  (let [big (max a b)
        small (min a b)]
    (if (= small 0)
      big
      (recur small (int (mod big small))))))

(gcd 5 7)



