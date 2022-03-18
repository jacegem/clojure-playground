(ns web.rest.user.router.user-token-router
  (:require [ring.util.http-response :refer [ok]]))


(defn user-token-router [ctx]
  {:get {:no-doc    true
         :summary   "health check"
         :responses {200 {:body {:message string?}}}
         :handler   (fn [_] (ok {:message "User Token"
                                 :context (str ctx)}))}})