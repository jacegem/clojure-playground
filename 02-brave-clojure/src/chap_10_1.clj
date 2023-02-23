(ns chap-10-1)


;; (def counter (ref 0))

;; (future
;;   (dosync (alter counter inc)
;;           (println @counter)
;;           (Thread/sleep 500)
;;           (alter counter inc)
;;           (println @counter)))
;; (Thread/sleep 250)
;; (println @counter)



;; (def counter-2 (ref 0))

;; (future
;;   (dosync (commute counter-2 inc)
;;           (println @counter-2)
;;           (Thread/sleep 500)
;;           (commute counter-2 inc)
;;           (println @counter-2)))
;; (Thread/sleep 250)
;; (println @counter-2) 

(defn sleep-print-update [sleep-time thread-name update-fn]
  (fn [state]
    (Thread/sleep sleep-time)
    (println (str thread-name ": " state))
    (update-fn state)))

(def counter (ref 0))

(future (dosync
         (alter counter (sleep-print-update 100 "Thread A" inc))))
(future (dosync
         (alter counter (sleep-print-update 150 "Thread B" inc))))

(prn @counter)

;; Create 2 bank accounts
(def acc1 (ref 100))
(def acc2 (ref 200))

;; How much money is there?
(println @acc1 @acc2)
;; => 100 200

;; Either both accounts will be changed or none
(defn transfer-money [a1 a2 amount]
  (dosync
   (alter a1 - amount)
   (throw (IllegalStateException. "Raise Exception"))
   (alter a2 + amount)
   amount))
; return amount from dosync block and function (just for fun)

;; Now transfer $20
(transfer-money acc1 acc2 20)
;; => 20

;; Check account balances again
(println @acc1 @acc2)
;; => 80 220

;; => We can see that transfer was successful

;m ;m   ;m  ;m 
;v
;m ;m
(println @acc1 @acc2)


(defn transfer-money-2 [a1 a2 amount]
  (dosync
   (commute a1 - amount)
   (throw (IllegalStateException. "Raise Exception"))
   (commute a2 + amount)
   amount))

(println @acc1 @acc2)

(transfer-money-2 acc1 acc2 20)


(println @acc1 @acc2)



(prn "======")


(def a1 (atom 0))
(def a2 (atom 1))
(def a3 (atom 2))
(def a4 (atom 3))

(def r1 (ref {:name "harry"}))
(def r2 (ref {:name "david"}))
(def r3 (ref {:name "wei"}))
(def r4 (ref {:name "chong"}))

(dosync
 (alter r1 {:grade \A})
 (alter r2 {:grade \B})
 (throw (Exception. "Interrupt ref!"))
 (alter r3 {:grade \C})
 (alter r4 {:grade \D}))

(prn "r1->" @r1 ", r2->" @r2 ", r3->" @r3 ", r4->" @r4)

(dosync
 (swap! a1 #(+ 10 %))
 (swap! a2 #(+ 10 %))
 (throw (Exception. "Interrupt atom!"))
 (swap! a3 #(+ 10 %))
 (swap! a4 #(+ 10 %)))

(prn "a1->" @a1 ", a2->" @a2 ", a3->" @a3 ", a4->" @a4)