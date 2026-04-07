import ./ApplicationDomain
import ./SecurityDomain
import ./ImageDecodingPolicy

type
  LoaderContext* = ref object
    allowCodeImport*: bool
    allowLoadBytesCodeExecution*: bool
    applicationDomain*: ApplicationDomain
    checkPolicyFile*: bool
    imageDecodingPolicy*: ImageDecodingPolicy
    securityDomain*: SecurityDomain

proc newLoaderContext*(checkPolicyFile: bool = false, applicationDomain: ApplicationDomain = nil, securityDomain: SecurityDomain = nil): LoaderContext =
  return LoaderContext(
    checkPolicyFile: checkPolicyFile,
    applicationDomain: applicationDomain,
    securityDomain: securityDomain,
    allowCodeImport: true,
    allowLoadBytesCodeExecution: true,
    imageDecodingPolicy: ImageDecodingPolicy.ON_DEMAND
  )
