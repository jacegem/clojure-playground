(ns notespace-user
  (:require [scicloj.notespace.v4.api :as notespace] ; the Notespace API
            #_[scicloj.kindly.v1.api :as kindly] ; specifying kinds of notes
            #_[scicloj.kindly.v1.kind :as kind] ; a collection of known kinds of notes
            #_[scicloj.kindly.v1.kindness :as kindness]))

; # 동시 프로그래밍과 병렬 프로그래밍

; ## 미래, 지연, 약속

; ### 미래

(future (Thread/sleep 4000)
        (println "I'll print after 4 seconds"))

(println "I'll print immediately")

; ### 역참조

(let [result (future (println "this prints once")
                     (+ 1 1))]
  (println "deref: " (deref result))
  (println "@: " @result))







(comment
  (notespace/restart!)
  '())




