import {deleteExam, updateExam} from "./client";
import {successNotification,errorNotification} from "./Notification";
import {Popconfirm, Space, Table} from "antd";
import Button from "antd/lib/button/button";
import TextArea from "antd/es/input/TextArea";


function ExpandableExamColumns({examSource ,fetchExams}){

    const info = [];
    info.push(examSource);
    console.log(examSource.id)
    console.log("Values passed to Expandable Exam Columns: " + JSON.stringify(info))
    console.log(info)
    //TODO: Figure out how to avoid this array-shit above.


    const expandableExamColumns = [

       {    title: 'Id',
            dataIndex: 'id',
            key: 'id'
        },

        {   title: 'Beschreibung',
            dataIndex: 'examSummary',
            key: 'examSummary',
            render: examSummary => {
                return <>
                    <TextArea defaultValue = {examSummary}
                              onPressEnter = {onSummarySubmit}
                              autoSize />
                    <div style={{ margin: '24px 0' }} />
                </>
            }

        },
        {   title: 'Action',
            key: 'operation',
            render: () => (
                <Space size = "middle">
                    <Popconfirm placement = "bottomRight"
                                title = "Sind Sie sicher, dass Sie die Prüfung löschen wollen?"
                                onConfirm={onDeletionConfirm}
                                okText="Ja"
                                cancelText="Nein">
                        <Button type="primary" danger size = "small">
                            löschen
                        </Button>
                    </Popconfirm>
                    <Button type = "primary" size = "small" >
                        absagen
                    </Button>
                </Space>
            )}
    ]

    const onSummarySubmit = value =>{

        //TODO: pass the value
        updateExam(examSource)


    }

    const onDeletionConfirm = () => {
        let id = examSource.id
        console.log("CONFIRM DELETE " + id)
        deleteExam(examSource.id)
            .then(()=>{
                console.log(`exam with id ${id} deleted`)
                successNotification(`Prüfung #${id} gelöscht`, `Die Prüfung wurde samt allen verbundenen Daten gelöscht.`)
                fetchExams()
            })
           .catch(err => {

               err.response.json().then(res=>
               {
                   errorNotification(`Fehler aufgetreten!`, "Die von Ihnen eingegebenen Veränderungen wurden nicht gespeichert. "
                   + "Sollte sich dieser Fehler wiederholen, wenden Sie sich an Ihren Software-Support unter +7 951 91 56789! "
               + `${res.message} status code: [${res.status}] ${res.error}`)
               })


           })
    }

    return <Table
        rowKey={examSource.id}
        columns = {expandableExamColumns}
        dataSource={info}
        pagination={false}/>
}
export default ExpandableExamColumns;