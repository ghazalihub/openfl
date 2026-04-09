import std/tables

type
  Dictionary*[K, V] = ref object
    table*: TableRef[K, V]

proc newDictionary*[K, V](weakKeys: bool = false): Dictionary[K, V] =
  # weakKeys is not supported in basic Nim tables
  return Dictionary[K, V](table: new(TableRef[K, V]))

proc `[]`*[K, V](self: Dictionary[K, V], key: K): V =
  return self.table[key]

proc `[]=`*[K, V](self: Dictionary[K, V], key: K, value: V) =
  self.table[key] = value

proc hasKey*[K, V](self: Dictionary[K, V], key: K): bool =
  return self.table.hasKey(key)

proc delete*[K, V](self: Dictionary[K, V], key: K) =
  self.table.del(key)
