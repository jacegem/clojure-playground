(ns kit.kit-selmer.web.page.layout
  (:require [clojure.java.io :as io]
            [ring.middleware.anti-forgery :refer [*anti-forgery-token*]]
            [ring.util.http-response :refer [ok]]
            [ring.util.response :refer [content-type]]
            [selmer.parser :as selmer]))

(def selmer-opts {:custom-resource-path (io/resource "html")})

(defn render
  [request template & [params]]
  (-> (selmer/render-file template
                          (assoc params :page template :csrf-token *anti-forgery-token*)
                          selmer-opts)
      (ok)
      (content-type "text/html; charset=utf-8")
      #_(wrap-content-type "text/html; charset=utf-8")))


(defn error-page
  "error-details should be a map containing the following keys:
   :status - error status
   :title - error title (optional)
   :message - detailed error message (optional)
   returns a response map with the error page as the body
   and the status specified by the status key"
  [error-details]
  {:status  (:status error-details)
   :headers {"Content-Type" "text/html; charset=utf-8"}
   :body    (selmer/render-file "error.html" error-details selmer-opts)})