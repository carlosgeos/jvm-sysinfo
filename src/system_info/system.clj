(ns system-info.system
  (:require [ring.util.response :as response])
  (:import java.lang.management.ManagementFactory))

(defn data
  "Returns a list of system information and metrics to show. array-map
  makes the JSON string display this same order"
  []
  (array-map
   :os-name                    (.getName (ManagementFactory/getOperatingSystemMXBean))
   :os-version                 (.getVersion (ManagementFactory/getOperatingSystemMXBean))
   :thread-count               (.getThreadCount (ManagementFactory/getThreadMXBean))
   :java-version               (System/getProperty "java.version")
   :java-home                  (System/getProperty "java.home")
   :clojure-version            (clojure-version)
   :architecture               (.getArch (ManagementFactory/getOperatingSystemMXBean))
   :cpu-cores                  (.getAvailableProcessors (ManagementFactory/getOperatingSystemMXBean))
   :cpu-endianness             (System/getProperty "sun.cpu.endian")
   :loaded-class-count         (.getLoadedClassCount (ManagementFactory/getClassLoadingMXBean))
   :system-load                (.getSystemLoadAverage (ManagementFactory/getOperatingSystemMXBean))
   :runtime-name               (System/getProperty "java.runtime.name")
   :runtime-version            (System/getProperty "java.runtime.version")
   :vm-name                    (System/getProperty "java.vm.name")
   :vm-version                 (System/getProperty "java.vm.version")
   :vm-vendor                  (System/getProperty "java.vm.vendor")
   :total-physical-memory      (.getTotalPhysicalMemorySize (ManagementFactory/getOperatingSystemMXBean))
   :heap-memory-init           (.getInit (.getHeapMemoryUsage (ManagementFactory/getMemoryMXBean)))
   :heap-memory-used           (.getUsed (.getHeapMemoryUsage (ManagementFactory/getMemoryMXBean)))
   :heap-memory-free           (.freeMemory (Runtime/getRuntime))
   :heap-memory-committed      (.getCommitted (.getHeapMemoryUsage (ManagementFactory/getMemoryMXBean)))
   :heap-memory-max            (.getMax (.getHeapMemoryUsage (ManagementFactory/getMemoryMXBean)))
   :non-heap-memory-init       (.getInit (.getNonHeapMemoryUsage (ManagementFactory/getMemoryMXBean)))
   :non-heap-memory-used       (.getUsed (.getNonHeapMemoryUsage (ManagementFactory/getMemoryMXBean)))
   :non-heap-memory-committed  (.getCommitted (.getNonHeapMemoryUsage (ManagementFactory/getMemoryMXBean)))
   :non-heap-memory-max        (.getMax (.getNonHeapMemoryUsage (ManagementFactory/getMemoryMXBean)))
   :uptime                     (.getUptime (ManagementFactory/getRuntimeMXBean))
   :utc-time                   (new java.util.Date)))


(defn gc-handler
  "Forces garbage collection and redirects to the root"
  [req]
  (System/gc)
  (response/redirect "/"))

(defn mem-handler
  "Creates an array with 10M ints (4 bytes each)"
  [req]
  (int-array 10000000)
  (response/redirect "/"))
