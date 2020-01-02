package main

import (
	"fmt"
	"os"
	"text/template"
)

type student struct {
	Name string
	Age  int
}

func returnBool(b bool) bool {
	return b
}
func main() {
	tepl := "My name is {{ . }}"
	tmpl, _ := template.New("test01").Parse(tepl)

	data := "jack"
	tmpl.Execute(os.Stdout, data)
	fmt.Println()
	t1 := template.Must(template.New("hello").Parse("hello world"))
	t1.Execute(os.Stdout, nil)

	fmt.Println()
	s2 := "LiLei"
	t2 := template.Must(template.New("test").Parse("Watch out, {{.}}!"))
	t2.Execute(os.Stdout, s2)

	fmt.Println()
	s3 := &student{Name: "Han Meimei", Age: 30}
	t3 := template.Must(template.New("test").Parse("{{.Name}} looks like more than {{.Age}} years old!"))
	t3.Execute(os.Stdout, s3)

	fmt.Println()
	marrage_info2 := map[string]bool{
		"HanMeimei": true,
		"LiLei":     false,
	}
	t4 := template.Must(template.New("test").Parse("Married: Han Meimei:{{.HanMeimei}}; Li Lei:{{.LiLei}}"))
	t4.Execute(os.Stdout, marrage_info2)

	fmt.Println()
	info5 := map[string]bool{
		"Han Meimei": true,
		"LiLei":      false,
	}
	t5 := template.Must(template.New("test").Parse(`Married: Han Meimei:{{index . "Han Meimei"}}; Li Lei:{{.LiLei}}`))
	t5.Execute(os.Stdout, info5)

	fmt.Println()
	infos6 := []string{"Han Meimei", "Lilei"}
	t6 := template.Must(template.New("test").Parse("Students List:" + "{{range .}}" + "\n{{.}}," + "{{end}}"))
	t6.Execute(os.Stdout, infos6)

	fmt.Println()
	infos7 := []string{"Han Meimei", "Lilei"}
	t7 := template.Must(template.New("test").Parse("Students List:" + "{{range $index, $_ := .}}" + "\n{{$index}}. {{.}}," + "{{end}}"))
	t7.Execute(os.Stdout, infos7)

	fmt.Println()
	s8 := "LiLei"
	t8 := template.Must(template.New("test").Parse(`{{if eq . "LiLei"}}Man{{else if eq . "Han Meimei"}}Women{{end}}`))
	t8.Execute(os.Stdout, s8)

	fmt.Println()
	funcs := template.FuncMap{
		"is_teacher_coming": returnBool,
	}
	t9 := template.New("test").Funcs(funcs)
	template.Must(t9.Parse("{{if is_teacher_coming .}}Carefully!{{end}}"))
	t9.Execute(os.Stdout, true)

	fmt.Println()
	s10 := &student{Name: "Han Meimei", Age: 30}
	t10 := template.New("test")
	template.Must(t10.Parse(`Name: {{.Name}}; {{template "age" .Age}}.`))
	// another way to name a template
	template.Must(t10.Parse(`{{define "age"}}Age: {{.}}{{end}}`))
	t10.Execute(os.Stdout, s10)
}
