type
  Security* = ref object

const
  REMOTE* = "remote"
  LOCAL_WITH_FILE* = "localWithFile"
  LOCAL_WITH_NETWORK* = "localWithNetwork"
  LOCAL_TRUSTED* = "localTrusted"

proc allowDomain*(domains: varargs[string]) = discard
proc allowInsecureDomain*(domains: varargs[string]) = discard
proc loadPolicyFile*(url: string) = discard

proc sandboxType*(): string = return LOCAL_TRUSTED
