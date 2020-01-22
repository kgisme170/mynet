package main
 
import (
    "os"
    "fmt"
    "github.com/op/go-logging"
)
 
var log = logging.MustGetLogger("example")
var format = logging.MustStringFormatter(
    `%{color}%{time:15:04:05.000} %{shortfunc} > %{level:.4s} %{id:03x}%{color:reset} %{message}`,
)
 
type Password string
 
func (p Password) Redacted() interface{} {
    return logging.Redact(string(p))
}
 
func main() {
    logFile, err := os.Create("log.txt")
    if err != nil{
        fmt.Println(err)
    }
    defer logFile.Close()
    backend1 := logging.NewLogBackend(logFile, "", 0)
    backend2 := logging.NewLogBackend(os.Stderr, "", 0)
 
    backend2Formatter := logging.NewBackendFormatter(backend2, format)
    backend1Leveled := logging.AddModuleLevel(backend1)
    backend1Leveled.SetLevel(logging.INFO, "")
 
    logging.SetBackend(backend1Leveled, backend2Formatter)
 
    log.Debugf("debug %s", Password("secret"))
    log.Info("info")
    log.Notice("notice")
    log.Warning("warning")
    log.Error("error")
    log.Critical("critical")
}