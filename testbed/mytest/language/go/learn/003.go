package main
import (
	"os"
	"github.com/spf13/cobra"
)
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

	//rootCmd.AddCommand(dumpOne.cobraCmd)
	rootCmd.Execute()
	os.Exit(0)
}
