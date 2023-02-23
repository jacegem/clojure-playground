(ns chap-9)


(deref (future (Thread/sleep 1000) 0) 10 5)
(realized? (future (Thread/sleep 1000)))
(let [f (future)]
  @f
  (realized? f))

(def jackson-5-dealy
  (delay (let [message "Just call my name and I'll be there"]
           (println "First deref:" message)
           message)))
(force jackson-5-dealy)

(def jackson-5-dealy-not-delay
  (let [message "Just call my name and I'll be there"]
    (println "First deref:" message)
    message))

(def gimli-headshots ["serious.jpg" "fun.jpg" "playful.jpg"])
(defn email-user [email-address]
  (println "Sending headshot notification to" email-address))
(defn upload-document "Needs to be iimplemented" [headshot]
  (println "upload " headshot))

(let [notify (delay (email-user "and-my-axe@gmail.com"))]
  (doseq [headshot gimli-headshots]
    (future (upload-document headshot) (force notify))))


; `promise` => deliver
(def my-promise-1 (promise))
(deliver my-promise-1 (+ 1 2))
@my-promise-1

(def my-promise-2 (promise))
(deliver my-promise-2 (+ 2 3))
(deliver my-promise-2 (+ 3 4))
@my-promise-2



(def yak-butter-international {:store "Yak Butter International"
                               :price 90
                               :smoothness 90})

(def butter-than-nothing {:store "Butter than Nothing"
                          :price 150
                          :smoothness 183})

(def baby-got-yak {:store "Baby Got Yak"
                   :price 94
                   :smoothness 99})

(defn mock-api-call [result]
  (Thread/sleep 1000)
  result)

(defn satisfactory?
  "If the butter meets our criteria, return the butter, else return false"
  [butter]
  (and (<= (:price butter) 100)
       (>= (:smoothness butter) 97)
       butter))

(time (some (comp satisfactory? mock-api-call)
            [yak-butter-international butter-than-nothing baby-got-yak]))

(time
 (let [butter-promise (promise)]
   (doseq [butter [yak-butter-international butter-than-nothing baby-got-yak]]
     (future (when-let [satisfactory-butter (satisfactory? (mock-api-call butter))]
               (deliver butter-promise satisfactory-butter))))
   (println "And the winnter is: " @butter-promise)))


(defmacro wait
  "Sleep `timeout` seconds before evaluate body" [timeout & body]
  `(do (Thread/sleep ~timeout) ~@body))

(wait 100 "Cheerio!")

(defn ^:macro-support wait-defn [timeout body]
  (do
    (Thread/sleep timeout) body))


(defmacro wait-2 [timeout & body]
  (wait-defn timeout `(~@body)))

(macroexpand-1 '(wait-2 100 "hi"))



(~(println "hi"))

(defn ^:macro-support-X ^:private >0? [n] (< 0 n))
(defn ^:macro-support ^:private >1? [n] (< 1 n))


(defmacro bigger-than-0 [n]
  (>0? n))

(bigger-than-0 -1)


(defmacro when-2
  "Evaluates test. If logical true, evaluates body in an implicit do."
  {:added "1.0"}
  [test & body]
  ~(list 'if test (cons 'do body)))