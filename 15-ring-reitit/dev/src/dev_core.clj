(ns dev-core
  (:require [integrant.core :as ig]
            [integrant.repl :refer [set-prep! go halt reset reset-all]]
            [integrant.repl.state :refer [system]]
            [web.core]))

(defn start []
  (set-prep! #(-> "dev/resources/config.edn"
                  slurp
                  ig/read-string))
  (go))

(def app (-> system :handler/app))

(comment
  (start)
  (reset)
  (halt)

  (app {:request-method :get
        :uri            "/"})
  '())





