package main
import (
	"bytes"
	"fmt"
	"io"
	"io/ioutil"
	"os"
	"strings"
	"time"
	"github.com/spf13/cobra"
	"github.com/spf13/viper"
	"go.uber.org/zap"
	"github.com/my"
)
var f = "config.yaml"

func readConfig(content io.Reader) string {
	config := viper.New()
	config.SetConfigType("yaml")
	err := config.ReadConfig(content)
	if err != nil {
		fmt.Printf("Error: ReadConfig: %s", err.Error())
		return ""
	}
	v := config.GetString("param1")
	return v
}

func main() {
	my.F1()
	rootCmd := &cobra.Command{
		Use: "003",
		Short: "test 003,",
		Long: `Message 001
Message 002
Message 003`,
		Version: "0.1.0",
		Run: func(cmd *cobra.Command, args [] string) {
			if len(args) == 0 {
				cmd.Help()
				os.Exit(0)
			}
		},
	}

	cobraCmd := &cobra.Command{
		Use: "mycmd",
		Short: "my usage",
		Long: `Long term`,
		Run: func(_ *cobra.Command, args [] string) {
			fmt.Println("mycmd")
			content, err := ioutil.ReadFile(f)
			if err != nil {
				fmt.Printf("Error: Config not found: %s\n", f)
				os.Exit(1)
			}
			reader := strings.NewReader(string(content))

			c := readConfig(reader)
			if err != nil {
				fmt.Printf("Error: %s", err.Error())
				os.Exit(2)
			}
			fmt.Println(c)
		},
	}

	cobraCmd.Flags().StringVarP(
		&f,
		"config",
		"c",
		"",
		"Path to config file, in json format")

	rootCmd.AddCommand(cobraCmd)
	rootCmd.Execute()

	var logger *zap.Logger
	var err error
	logger, err = zap.NewProduction()
	if err == nil {
		fmt.Println("zap logger error")
	}
	fmt.Println("%T", logger)
	fmt.Println(logger)

	defer logger.Sync()
	logger.Info("----failed to fetch URL----",
		zap.String("url", "http://example.com"),
		zap.Int("attempt", 3),
		zap.Duration("backoff", time.Second),
	)
	config := viper.New()
	config.SetConfigType("yaml") // or viper.SetConfigType("YAML")

	buf := bytes.NewBufferString("hello")
	s := []byte(" world")
	buf.Write(s)
	fmt.Println(buf.String())

	var yamlExample = []byte(`
Hacker: true
name: steve
hobbies:
- skateboarding
- snowboarding
- go
clothing:
jacket: leather
trousers: denim
age: 35
eyes : brown
beard: true`)

	content := bytes.NewBuffer(yamlExample)
	//fmt.Println(content)
	err = config.ReadConfig(content)
	if err != nil {
		fmt.Printf("Error: ReadConfig: %s", err.Error())
		os.Exit(1)
	}

	fmt.Println("name=",config.GetString("name"))
	os.Exit(0)
}
