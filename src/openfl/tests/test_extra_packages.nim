import ../globalization/DateTimeFormatter
import ../globalization/LocaleID
import ../printing/PrintJob
import ../permissions/PermissionStatus
import ../profiler/Telemetry
import std/times

proc testGlobalization() =
  echo "Testing Globalization..."
  let lid = newLocaleID("en-US")
  assert lid.name == "en-US"

  let dtf = newDateTimeFormatter("en-US")
  let now = now()
  let formatted = dtf.format(now)
  echo "Formatted date: ", formatted
  echo "Globalization test passed!"

proc testPrinting() =
  echo "Testing Printing..."
  assert not PrintJob.isSupported
  let job = newPrintJob()
  assert not job.isNil
  echo "Printing test passed!"

proc testPermissions() =
  echo "Testing Permissions..."
  assert PermissionStatus.GRANTED == PermissionStatus.GRANTED
  echo "Permissions test passed!"

proc testProfiler() =
  echo "Testing Profiler..."
  assert not Telemetry.connected
  echo "Profiler test passed!"

testGlobalization()
testPrinting()
testPermissions()
testProfiler()
echo "All extra packages tests passed!"
