(ns living-clojure.chap-2.1-논리에-따라-흐름제어)

(true? true)
(false? false)
(nil? nil)
;=> true
(not true)
;=> false
(not nil)
;=> true
(= :drinkme :drinkme)
;=> true
(not= :drinkme 4)
(empty? [:table :door :key])
;=> false
(empty? [])
;=> true
