(ns system-info.api
  (:require
   [jsonista.core :as j]
   [system-info.system :as sys]))

(defn api-handler
  [_req]
  {:status  200
   :headers {"Content-Type" "application/json; charset=utf-8"}
   :body    (j/write-value-as-string (sys/data) j/default-object-mapper)})
