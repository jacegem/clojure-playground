(ns luminus-my-app.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [luminus-my-app.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[luminus-my-app started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[luminus-my-app has shut down successfully]=-"))
   :middleware wrap-dev})
