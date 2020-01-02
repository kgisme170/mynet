package main

import (
	"bytes"
	"fmt"
	"io/ioutil"
	"net/http"
	"os"
	"strings"
)

func readTemplateFile(templateFile string) string {
	jsonFile, err := os.Open(templateFile)
	if err != nil {
		fmt.Println(err)
	}
	defer jsonFile.Close()
	fmt.Println("Successfully Opened users.json")

	byteValue, _ := ioutil.ReadAll(jsonFile)
	return string(byteValue)
}

func replaceTitle(jsonStr string, title string) string {
	return strings.Replace(jsonStr, "TO_BE_TITLE", title, -1)
}

func min(x, y int) int {
	if x < y {
		return x
	}
	return y
}

func createIndexPattern(url string, username string, password string, index string) error {
	indexStr := readTemplateFile("IndexPatternTemplate.json")
	jsonStr := replaceTitle(indexStr, index)

	newurl := url + "/api/saved_objects/index-pattern/" + index
	req, err := http.NewRequest("POST", newurl, bytes.NewBuffer([]byte(jsonStr)))
	if err != nil {
		fmt.Println(err)
		return fmt.Errorf("cannot connect")
	}
	req.Header.Set("Content-Type", `application/json`)
	req.Header.Set("kbn-xsrf", "true")
	req.SetBasicAuth(username, password)
	client := &http.Client{}
	resp, err := client.Do(req)
	if err != nil {
		return fmt.Errorf("cannot do request")
	}
	defer resp.Body.Close()

	fmt.Println("response Status:", resp.Status)
	body, _ := ioutil.ReadAll(resp.Body)
	fmt.Println("response Body:", string(body))
	return nil
}

func createVisualization(url string, username string, password string, index string) error {
	indexStr := readTemplateFile("VisualizationTemplate.json")
	jsonStr := replaceTitle(indexStr, index)
	fmt.Println(jsonStr)
	newurl := url + "/api/saved_objects/visualization/" + index
	fmt.Println(newurl)
	req, err := http.NewRequest("POST", newurl, bytes.NewBuffer([]byte(jsonStr)))
	if err != nil {
		fmt.Println(err)
		return fmt.Errorf("cannot connect")
	}
	req.Header.Set("Content-Type", `application/json`)
	req.Header.Set("kbn-xsrf", "true")
	req.SetBasicAuth(username, password)
	client := &http.Client{}
	resp, err := client.Do(req)
	if err != nil {
		return fmt.Errorf("cannot do request")
	}
	defer resp.Body.Close()

	fmt.Println("response Status:", resp.Status)
	return nil
}

var dashboardReference = `
		{
			"id": "TO_BE_ID",
			"name": "TO_BE_PANEL_NAME",
			"type": "visualization"
		}`

var panelTemplate = []string{
	`{\"version\":\"7.4.2\",\"gridData\":{\"x\": 0,\"y\": 0,\"w\":24,\"h\":15,\"i\":\"ca288e60-b00b-4cab-af5c-f33622ba928b\"},\"panelIndex\":\"ca288e60-b00b-4cab-af5c-f33622ba928b\",\"embeddableConfig\":{},\"panelRefName\":\"panel_0\"}`,
	`{\"version\":\"7.4.2\",\"gridData\":{\"x\":24,\"y\": 0,\"w\":24,\"h\":15,\"i\":\"d70fbd2b-a3f3-44c3-bf12-b85d9828d5df\"},\"panelIndex\":\"d70fbd2b-a3f3-44c3-bf12-b85d9828d5df\",\"embeddableConfig\":{},\"panelRefName\":\"panel_1\"}`,
	`{\"version\":\"7.4.2\",\"gridData\":{\"x\":24,\"y\":15,\"w\":24,\"h\":15,\"i\":\"60d91eb6-eb4c-45dd-8698-3f3d72be1bfa\"},\"panelIndex\":\"60d91eb6-eb4c-45dd-8698-3f3d72be1bfa\",\"embeddableConfig\":{},\"panelRefName\":\"panel_2\"}`,
	`{\"version\":\"7.4.2\",\"gridData\":{\"w\":24,\"h\":15,\"x\": 0,\"y\":15,\"i\":\"7e34bcc9-7ca9-445b-b320-d981a5e517ab\"},\"panelIndex\":\"7e34bcc9-7ca9-445b-b320-d981a5e517ab\",\"embeddableConfig\":{},\"panelRefName\":\"panel_3\"}`,
	`{\"version\":\"7.4.2\",\"gridData\":{\"w\":24,\"h\":15,\"x\": 0,\"y\":30,\"i\":\"0e9c2151-7e7d-441f-9fa6-8f6c1c9b3d5f\"},\"panelIndex\":\"0e9c2151-7e7d-441f-9fa6-8f6c1c9b3d5f\",\"embeddableConfig\":{},\"panelRefName\":\"panel_4\"}`,
	`{\"version\":\"7.4.2\",\"gridData\":{\"w\":24,\"h\":15,\"x\":24,\"y\":30,\"i\":\"8e20689f-d3e2-4b5d-b1c6-0e22f217b185\"},\"panelIndex\":\"8e20689f-d3e2-4b5d-b1c6-0e22f217b185\",\"embeddableConfig\":{},\"panelRefName\":\"panel_5\"}`,
	`{\"version\":\"7.4.2\",\"gridData\":{\"w\":24,\"h\":15,\"x\": 0,\"y\":45,\"i\":\"f6afd343-9c1b-4393-b99e-1bba784fd14b\"},\"panelIndex\":\"f6afd343-9c1b-4393-b99e-1bba784fd14b\",\"embeddableConfig\":{},\"panelRefName\":\"panel_6\"}`,
	`{\"version\":\"7.4.2\",\"gridData\":{\"w\":24,\"h\":15,\"x\":24,\"y\":45,\"i\":\"ff1ddcb2-a0d0-432a-bc9b-29c5fa0e5294\"},\"panelIndex\":\"ff1ddcb2-a0d0-432a-bc9b-29c5fa0e5294\",\"embeddableConfig\":{},\"panelRefName\":\"panel_7\"}`,
	`{\"version\":\"7.4.2\",\"gridData\":{\"w\":24,\"h\":15,\"x\": 0,\"y\":60,\"i\":\"4209eca1-78da-4e5e-a925-ec3242d5313c\"},\"panelIndex\":\"4209eca1-78da-4e5e-a925-ec3242d5313c\",\"embeddableConfig\":{},\"panelRefName\":\"panel_8\"}`,
	`{\"version\":\"7.4.2\",\"gridData\":{\"w\":24,\"h\":15,\"x\":24,\"y\":60,\"i\":\"ebc3af14-e536-4d15-ac64-6bca187e2874\"},\"panelIndex\":\"ebc3af14-e536-4d15-ac64-6bca187e2874\",\"embeddableConfig\":{},\"panelRefName\":\"panel_9\"}`,
}

var dashboardTemplate = `
{
    "attributes": {
        "description": "",
        "hits": 0,
        "kibanaSavedObjectMeta": {
            "searchSourceJSON": "{\"query\":{\"query\":\"\",\"language\":\"kuery\"},\"filter\":[]}"
        },
        "optionsJSON": "{\"useMargins\":true,\"hidePanelTitles\":false}",
        "panelsJSON": "[TO_BE_PANEL_DESCRIPTION]",
        "timeRestore": false,
        "title": "TO_BE_TITLE",
        "version": 1
    },
    "references": [
		TO_BE_REFERENCE
    ]
}
`

func genDashboardReference(id string, panelNumber int) string {
	withID := strings.Replace(dashboardReference, "TO_BE_ID", id, -1)
	withName := strings.Replace(withID, "TO_BE_PANEL_NAME", fmt.Sprintf("panel_%d", panelNumber), -1)
	return withName
}

func createDashboard(url string, username string, password string, eventName string, upstream []string) error {
	index := strings.ToLower(eventName) // we usually use lower case to create kibana

	// replace ids
	var length = min(len(upstream), 10)

	panelReference := panelTemplate[0]
	panelReference += ","
	eventReference := genDashboardReference(index, 0)
	eventReference += ","
	for i := 1; i < length; i++ { // index + upstreams, so "+1"
		upstreamEvent := strings.ToLower(upstream[i])
		eventReference += genDashboardReference(upstreamEvent, i)
		panelReference += panelTemplate[i]
		if i != length-1 { // last json element doesn't need ","
			eventReference += ","
			panelReference += ","
		}
	}
	fmt.Println(eventReference)
	fmt.Println("------------------")
	fmt.Println(panelReference)
	fmt.Println("------------------")
	jsonStr := strings.Replace(dashboardTemplate, "TO_BE_TITLE", "slabddashboard_"+index, -1)
	jsonStr = strings.Replace(jsonStr, "TO_BE_REFERENCE", eventReference, -1)
	jsonStr = strings.Replace(jsonStr, "TO_BE_PANEL_DESCRIPTION", panelReference, -1)
	fmt.Println(jsonStr)

	// end replace
	//return nil

	newurl := url + "/api/saved_objects/dashboard/" + index

	req, _ := http.NewRequest("POST", newurl, bytes.NewBuffer([]byte(jsonStr)))
	/*
		if err != nil {
			logger.Error("cannot connect http for dashboard")
			return errors.Wrap(err, "cannot do request for dashboard")
		}*/
	req.Header.Set("Content-Type", `application/json`)
	req.Header.Set("kbn-xsrf", "true")
	req.SetBasicAuth(username, password)
	client := &http.Client{}
	resp, err := client.Do(req)
	if err != nil {
		//logger.Error("create dashboard response error")
		return fmt.Errorf("create dashboard response error")
	}
	defer resp.Body.Close()

	body, err := ioutil.ReadAll(resp.Body)
	fmt.Println(string(body))
	/*logger.Info(string(body))
	if err != nil {
		logger.Error("create dashboard response error")
		return errors.Wrap(err, "create dashboard response error")
	}
	logger.Info("Successfully created dashboard on kibana: " + index)*/
	return nil
}

func deleteSavedObject(url string, username string, password string, objectType string, id string) error {
	newurl := url + "/api/saved_objects/" + objectType + "/" + id
	fmt.Println(newurl)
	req, err := http.NewRequest("DELETE", newurl, nil)
	if err != nil {
		fmt.Println(err)
		return fmt.Errorf("cannot connect")
	}
	req.Header.Set("Content-Type", `application/json`)
	req.Header.Set("kbn-xsrf", "true")
	req.SetBasicAuth(username, password)
	client := &http.Client{}
	resp, err := client.Do(req)
	if err != nil {
		return fmt.Errorf("cannot do request")
	}
	defer resp.Body.Close()

	fmt.Println("response Status:", resp.Status)
	return nil
}

func main() {
	esurl := `http://sa-elasticsearch.trafficmanager.net`
	fmt.Println("URL:>", esurl)
	kibanaurl := `http://sa-kibana.trafficmanager.net`
	username := "sadevs"
	password := "mu4m0JCGor16|2(T7nJKcL6eW=.I91y&"
	//pingElasticSearch(esurl, username, password)

	//index := strings.ToLower("mytitle")
	fmt.Println("---------------")
	//createIndexPattern(kibanaurl, username, password, index)
	//createVisualization(kibanaurl, username, password, index)

	var upstream = []string{
		"uet_offlineconversion_done",
		"uet_offlineconversion_part1_done",
		"subjectareas_conversion_clickuseradactivity_done",
		"impressionmsanhourly_done",
		//"uet_viewthrough_uservists_hourly_done",
		//"adcenter_searchquery_agg_onstripe_done",
		//"adcenter_kpi_agg_done",
		//"adcenter_kpi_agg_conversion_done",
		"adcenter_advbi_agg_onstripe_batchonlyconv_done",
		"adcenter_ca_agg_deputy_hourly_onstripe_batchonlyconv_done",
	}
	index := "adsbi_sa_streamingclickbajatableupdate_done"
	createDashboard(kibanaurl, username, password, index, upstream)

	//slabdvisualization_adsbi_sa_streamingclickbajatableupdate_done
	//deleteSavedObject(kibanaurl, username, password, "dashboard", index)
}
