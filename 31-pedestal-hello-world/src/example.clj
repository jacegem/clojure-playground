(ns example
  (:require [io.pedestal.http :as http]
            [com.walmartlabs.lacinia.pedestal2 :as lp]
            [com.walmartlabs.lacinia.schema :as schema]
            [ns-tracker.core :refer [ns-tracker]]))


(def hello-schema
  (schema/compile
   {:queries
    {:hello
        ;; String is quoted here; in EDN the quotation is not required 
        ;; You could also use :String
     {:type 'String
      :resolve (constantly "world 3")}}
    :subscriptions {}}))

;; Use default options:
(def service (lp/default-service hello-schema nil))


;; This is an adapted service map, that can be started and stopped.
;; From the REPL you can call http/start and http/stop on this service:
(defonce runnable-service (http/create-server service))

(defn -main
  "The entry-point for 'lein run'"
  [& args]
  (println "\nCreating your server...")
  (http/start runnable-service))

(defn- ns-reload [track]
  (try
    (doseq [ns-sym (track)]
      (require ns-sym :reload))
    (catch Throwable e (.printStackTrace e))))

(defn watch
  ([] (watch ["src"]))
  ([src-paths]
   (let [track (ns-tracker src-paths)
         done (atom false)]
     (doto
      (Thread. (fn []
                 (while (not @done)
                   (ns-reload track)
                   (Thread/sleep 500))))
       (.setDaemon true)
       (.start))
     (fn [] (swap! done not)))))

(comment
  (http/start runnable-service)
  (watch)
  '())