package main
import (
	"fmt"
	"io"
	"io/ioutil"
	"os"
	"strings"
	"github.com/spf13/cobra"
	"github.com/spf13/viper"
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
	//fmt.Println("param1:", v)
	return v
}
func main() {
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
			//fmt.Println(content)
			reader := strings.NewReader(string(content))

			c := readConfig(reader)
			if err != nil {
				fmt.Printf("Error: %s", err.Error())
				os.Exit(2)
			}
			fmt.Println(c)
		},
	}
	rootCmd.AddCommand(cobraCmd)
	rootCmd.Execute()
	os.Exit(0)
}
