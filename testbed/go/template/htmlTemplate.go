package main

import (
	"html/template"
	"os"
)

/*
const tmplSrc = `
<table>
	<tr align="left">
	{{ range . }}
		{{if eq .Value 0 }}
			<td style="color: rgba(55, 248, 255, 1);">{{.Name}}</td>
		{{ else }}
			<td style="color: rgba(200, 200, 55, 1);">{{.Name}}</td>
		{{ end }}
	{{ end }}
	</tr>
</table>`
*/
const tmplSrc = `
<table>
	{{ range . }}<tr align="left"><th>{{.Name}}</th><th>{{.Value}}</th></tr>
	{{ end }}
</table>`

var tmpl = template.Must(template.New("tmpl").Parse(tmplSrc))

type Week struct {
	Name  string
	Value int
}

var weeks = []Week{
	{"01", 0},
	{"02", 1},
	{"03", 0},
	{"04", 0},
	{"05", 1},
}

func main() {
	tmpl.Execute(os.Stdout, weeks)
}
