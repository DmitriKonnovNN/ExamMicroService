import {addNewExam} from "./client";
import {successNotification, errorNotification, warningNotification} from "./Notification";

import {Drawer, Input, Col, Select, Form, Row, Button, Spin, DatePicker, Space} from 'antd';
import {LoadingOutlined} from "@ant-design/icons";
import {useState} from "react";
import Checkbox from "antd/es/checkbox/Checkbox";
import TextArea from "antd/es/input/TextArea";

const {Option} = Select;

const antIcon = <LoadingOutlined style={{ fontSize: 24 }} spin />;


//TODO: Email front end VALIDATION!
function ExamDrawerForm({showDrawer, setShowDrawer,fetchExams}) {

    const [submittingExam, setSubmittingExam] = useState(false);
        const onCLose = () => setShowDrawer(false);

    const renderExamFromForm = exam => {
        console.log("EXAM:" + exam)
        let types = [];
        for (let type of exam.examType){
            types.push({id:type})
        }
        console.log(types)
        return {
            examName: exam.examName,
            examLevel: exam.examLevel,
            status: exam.examStatus,
            examSummary: exam.summary,
            emailPersonInChargeForExam: exam.emailInCharge,
            examType: types
        }

    }
    const onChange = (date, dateString) => {

    }

    const onFinish = exam => {
        let exam1 = renderExamFromForm(exam)
        console.log("EXAM1: " + exam1)
        setSubmittingExam(true)
        addNewExam(exam1)
            .then(()=> {
                console.log("exam added " + JSON.stringify(exam1))
                onCLose()
                successNotification(`Prüfung ${exam1.examName} wurde kreiert!`,`Niveau: ${exam1.examLevel}`)
                fetchExams()})
            .then(()=>setSubmittingExam(false))
            .catch(err => {
                err.response.json().then(res=>{
                    console.log(`${res.message} status code: [${res.status}] ${res.error}`)
                    errorNotification(`Fehler aufgetreten!`, `Die von Ihnen eingegebenen Veränderungen wurden nicht gespeichert.
                    Sollte sich dieser Fehler wiederholen, wenden Sie sich an Ihren Software-Support unter +7 951 91 56789!
                    Teilen Sie dem Support mit: status code: [${res.status}] ${res.error}`)})
            })
            .finally(()=>{
                setSubmittingExam(false)
                onCLose()})
    };


    const onFinishFailed = errorInfo => {
        warningNotification("Angaben fehlen!")
    };

    return <Drawer
        title="neue Prüfung erstellen"
        width={720}
        onClose={onCLose}
        visible={showDrawer}
        bodyStyle={{paddingBottom: 80}}
        footer={
            <div
                style={{
                    textAlign: 'right',
                }}
            >
                <Button danger onClick={onCLose} style={{marginRight: 8}}>
                    verwerfen
                </Button>
            </div>
        }
    >
        <Form layout="vertical"
              onFinishFailed={onFinishFailed}
              onFinish={onFinish}
              hideRequiredMark>
            <Row gutter={16}>
                <Col span={12}>
                    <Form.Item
                        name="examName"
                        label="Prüfungsname"
                        rules={[{required: true, message: 'Bitte Prüfungsnamen eingeben'}]}
                    >
                        <Input placeholder="Prüfungsnamen eingeben"/>
                    </Form.Item>
                </Col>
               <Col span={12}>
                    <Form.Item
                        name="examStatus"
                        label="Status"
                        rules={[{required: true, message: 'Status wählen'}]}
                    >
                        <Select placeholder="Status wählen">
                            <Option value="Anmeldung läuft">Anmeldung läuft</Option>
                            <Option value="Anmeldung abgeschlossen">Anmeldung abgeschlossen</Option>
                            <Option value="Eingeplant">Eingeplant</Option>
                        </Select>
                    </Form.Item>
                </Col>
            </Row>
            <Row gutter={16}>
                <Col span={12}>
                    <Form.Item
                        name="examLevel"
                        label="Niveau"
                        rules={[{required: true, message: 'Gib Niveau ei du Gscherter'}]}
                    >
                        <Select placeholder="Niveau eingeben">
                            <Option value="C2">C2</Option>
                            <Option value="C1">C1</Option>
                            <Option value="B2">B2</Option>
                            <Option value="B1">B1</Option>
                            <Option value="A2">A2</Option>
                            <Option value="A1">A1</Option>
                        </Select>
                    </Form.Item>
                </Col>
                <Col span= {12}>
                    <Form.Item name="emailInCharge"
                               label = "Email der zuständigen Person"
                               rules={[{required: true, message: 'Bitte Email eingeben'}]}
                    >
                        <Input placeholder = "Email eingeben"/>
                    </Form.Item>
                </Col>
            </Row>
            <Row>
                <Form.Item name="examType" label="Prüfungstyp">
                    <Checkbox.Group>
                        <Row>
                            <Col span={8}>
                                <Checkbox
                                    value="1"
                                    style={{
                                        lineHeight: '32px',
                                    }}
                                >
                                    Wirtschaft
                                </Checkbox>
                            </Col>
                            <Col span={8}>
                                <Checkbox
                                    value="2"
                                    style={{
                                        lineHeight: '32px',
                                    }}

                                >
                                    Jugendliche
                                </Checkbox>
                            </Col>
                            <Col span={8}>
                                <Checkbox
                                    value="3"
                                    style={{
                                        lineHeight: '32px',
                                    }}
                                >
                                    Erwachsene
                                </Checkbox>
                            </Col>
                            <Col span={8}>
                                <Checkbox
                                    value="4"
                                    style={{
                                        lineHeight: '32px',
                                    }}
                                    disabled
                                >
                                    Migration
                                </Checkbox>
                            </Col>
                            <Col span={8}>
                                <Checkbox
                                    value="5"
                                    style={{
                                        lineHeight: '32px',
                                    }}
                                >
                                    Geflüchtete
                                </Checkbox>
                            </Col>
                            <Col span={8}>
                                <Checkbox
                                    value="6"
                                    style={{
                                        lineHeight: '32px',
                                    }}
                                >
                                    Medizin
                                </Checkbox>
                            </Col>
                        </Row>
                    </Checkbox.Group>
                </Form.Item>
            </Row>
            <Row gutter = {16}>
                <Form.Item name = "examDate" label = "Prüfungstermine">
                    <Col span>
                        <Space direction = "vertical">
                            <DatePicker className = "writing" placeholder = "schriftlich" onChange={onChange} />
                        </Space>
                        <Space direction = "vertical">
                            <DatePicker className = "oral" placeholder = "mündlich" onChange={onChange} />
                        </Space>
                    </Col>
                </Form.Item>
            </Row>
            <Row>
                <Col span = {24}>
                    <Form.Item name = "summary" label = "kurze Beschreiung">
                        <TextArea rows = {4} placeholder = "Beschreiben Sie die Prüfung wenn nötig"/>
                    </Form.Item>
                </Col>
            </Row>
            <Row>
                <Col span={12}>
                    <Form.Item >
                        <Button type="primary" htmlType="submit">
                            Fertig!
                        </Button>
                    </Form.Item>
                </Col>
            </Row>
            <Row> {submittingExam && <Spin indicator={antIcon}/>}</Row>
        </Form>
    </Drawer>
}

export default ExamDrawerForm;