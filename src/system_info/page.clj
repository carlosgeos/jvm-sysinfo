(ns system-info.page
  (:require [system-info.system :as sys]
            [clojure.contrib.humanize :refer [duration filesize]]
            [hiccup.element :refer [link-to]]
            [hiccup.page :as hiccup]
            [ring.util.response :as response]))

(defn html-handler
  [req]
  (let [data (sys/data)]
    (-> (hiccup/html5
         [:head
          [:title "System info"]
          (hiccup/include-css "https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.css")
          (hiccup/include-css "https://cdnjs.cloudflare.com/ajax/libs/milligram/1.4.1/milligram.css")]
         [:body
          [:div.container {:style "margin-top: 5em"}
           [:div.row
            [:div.column [:h3 "Property"]]
            [:div.column [:h3 "Value"]]]
           [:div.row
            [:div.column "OS name"]
            [:div.column (:os-name data)]]
           [:div.row
            [:div.column "OS version"]
            [:div.column (:os-version data)]]
           [:div.row
            [:div.column "Thread count"]
            [:div.column (:thread-count data)]]
           [:div.row
            [:div.column "Java version"]
            [:div.column (:java-version data)]]
           [:div.row
            [:div.column "Java home"]
            [:div.column (:java-home data)]]
           [:div.row
            [:div.column "Clojure version"]
            [:div.column (:clojure-version data)]]
           [:div.row
            [:div.column "Architecture"]
            [:div.column (:architecture data)]]
           [:div.row
            [:div.column "Available CPU cores"]
            [:div.column (:cpu-cores data)]]
           [:div.row
            [:div.column "System load average"]
            [:div.column (:system-load data)]]
           [:div.row
            [:div.column "CPU endianness"]
            [:div.column (:cpu-endianness data)]]
           [:div.row
            [:div.column "Loaded class count"]
            [:div.column (:loaded-class-count data)]]
           [:div.row
            [:div.column "Runtime name"]
            [:div.column (:runtime-name data)]]
           [:div.row
            [:div.column "Runtime version"]
            [:div.column (:runtime-version data)]]
           [:div.row
            [:div.column "VM name"]
            [:div.column (:vm-name data)]]
           [:div.row
            [:div.column "VM version"]
            [:div.column (:vm-version data)]]
           [:div.row
            [:div.column "VM vendor"]
            [:div.column (:vm-vendor data)]]
           [:div.row
            [:div.column "Total physical memory"]
            [:div.column (filesize (:total-physical-memory data) :binary true)]]
           [:div.row
            [:div.column "Heap memory (init)"]
            [:div.column (filesize (:heap-memory-init data) :binary true)]]
           [:div.row
            [:div.column "Heap memory (currently used)"]
            [:div.column {:style "color: #FF4500"}
             (filesize (:heap-memory-used data) :binary true)]]
           [:div.row
            [:div.column "Heap memory (free)"]
            [:div.column {:style "color: green"}
             (filesize (:heap-memory-free data) :binary true)]]
           [:div.row
            [:div.column "Heap memory (committed)"]
            [:div.column (filesize (:heap-memory-committed data) :binary true)]]
           [:div.row
            [:div.column "Heap memory (max)"]
            [:div.column (filesize (:heap-memory-max data) :binary true)]]
           [:div.row
            [:div.column "Non-heap memory (init)"]
            [:div.column (filesize (:non-heap-memory-init data) :binary true)]]
           [:div.row
            [:div.column "Non-heap memory (currently used)"]
            [:div.column (filesize (:non-heap-memory-used data) :binary true)]]
           [:div.row
            [:div.column "Non-heap memory (committed)"]
            [:div.column (filesize (:non-heap-memory-committed data) :binary true)]]
           [:div.row
            [:div.column "Non-heap memory (max)"]
            [:div.column (filesize (:non-heap-memory-max data) :binary true)]]
           [:div.row
            [:div.column "Uptime"]
            [:div.column (duration (:uptime data) {:number-format str})]]
           [:div.row
            [:div.column "Current UTC time"]
            [:div.column (:utc-time data)]]


           [:div.row {:style "margin-top: 2em"}
            [:div.column
             (link-to "/mem" [:button.button-outline "Generate 10M ints"])]
            [:div.column
             (link-to "/gc" [:button.button "Garbage collect"])]
            [:div.column]]]])
        (response/response)
        (response/header "content-type" "text/html"))))
