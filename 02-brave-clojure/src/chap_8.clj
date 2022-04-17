(ns chap-8)


(when true (prn "when-true"))
;; when 은 매크로 이다 (O/X)
(macroexpand '(when true (prn "when-true")))
;; (if true (do (prn "when-true")))

(defmacro infix
  "Use this macro when you pine for the notation of your childhood"
  [infixed]
  (list (second infixed) (first infixed) (last infixed)))

(infix (1 + 1))
(macroexpand '(infix (1 + 1)))

(defmacro infix-2 [[operand1 op operand2]]
  (list op operand1 operand2))

(defmacro my-print-whoopsie [expression]
  (list 'let ['result expression]
        (list println 'result)
        'result))

;; 인용부호
'(+ 1 2)
; 3
(quote (+ 1 2))
; (+ 1 2)

+
'+
(quote sweating-to-the-oldies)
;; sweating-to-the-oldies

(quote x)

`+
;; clojure.core/+
`(+ 1 2)
;; (clojure.core/+ 1 2)

`(+ 1 (inc 1))
`(+ 1 ~(inc 1))
`(+ 1 ~ (inc 1))


(list '+ 1 (inc 1))
`(+ 1 ~(inc 1))


(defmacro code-critic
  "Phrases are courtesy Hermes conrad from Futurama"
  [bad good]
  (list 'do
        (list 'println "Greate squid of Madrid, this is bad code:"
              (list 'quote bad))
        (list 'println "Sweet gorilla of Manila, this is good code:"
              (list 'quote good))))

(code-critic (1 + 1) (+ 1 1))

(defmacro code-critic-2
  "Phrases are courtesy Hermes conrad from Futurama"
  [bad good]
  `(do
     (println "Greate squid of Madrid, this is bad code:"
              '~bad)
     (println "Sweet gorilla of Manila, this is good code:"
              '~good)))

(code-critic-2 (1 + 1) (+ 1 1))

(defn criticize-code [criticism code]
  `(println ~criticism (quote ~code)))

(defmacro code-critic-3 [bad good]
  `(do ~(map #(apply criticize-code %)
             [["Cursed bacteria of Liberia, this is bad code:" bad]
              ["Sweet sacred boa of Western and Eastern Samoa, this is good code:" good]])))

(code-critic-3 (1 + 1) (+ 1 1))

(defmacro code-critic-3 [bad good]
  `(do ~@(map #(apply criticize-code %)
              [["Cursed bacteria of Liberia, this is bad code:" bad]
               ["Sweet sacred boa of Western and Eastern Samoa, this is good code:" good]])))

(code-critic-3 (1 + 1) (+ 1 1))



