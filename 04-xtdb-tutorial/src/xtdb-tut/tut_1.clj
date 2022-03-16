


(ns xtdb-tut.tut-1
  (:require [xtdb.api :as xt]
            [xtdb-tut.core :refer [node]]))

(def manifest
  {:xt/id :manifest
   :pilot-name "Johanna"
   :id/rocket "SB002-sol"
   :id/employee "22910x2"
   :badges "SETUP"
   :cargo ["stereo" "gold fish" "slippers" "secret note"]})

(xt/submit-tx node [[::xt/put manifest]])
(xt/sync node)

(xt/entity (xt/db node) :manifest)
