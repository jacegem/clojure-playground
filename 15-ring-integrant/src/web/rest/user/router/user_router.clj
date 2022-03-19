
(ns web.rest.user.router.user-router
  (:require [ring.util.http-response :as hr]
            [web.rest.user.router.user-token-router :refer [user-token-router]]))

(defn user-router [ctx]
  ["/user" {:swagger {:tags ["user"]}}
   ["" {:get {:no-doc    true
              :summary   "health check"
              :responses {200 {:body {:message string?}}}
              :handler   (fn [_] (hr/ok {:message "Hello Web"}))}}]
   ["/token" (user-token-router ctx)]
  ;;  #_["/info" (user-info context)]
   ])
