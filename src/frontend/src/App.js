import './App.css';


import {getAllExams} from "./client";
import {
    useState,
    useEffect
} from "react";

import {
    Layout,
    Menu,
    Breadcrumb,
    Table,
    Tag,
    Spin,
    Empty,
    Switch,
    Badge
} from 'antd';

import {
    DesktopOutlined,
    PieChartOutlined,
    FileOutlined,
    TeamOutlined,
    UserOutlined,
    LoadingOutlined,
    EditOutlined, PlusOutlined
} from '@ant-design/icons';
import Button from "antd/lib/button/button";
import ExamDrawerForm from "./ExamDrawerForm";
import ExpandableExamColumns from "./ExpandableExamColumns";
import {errorNotification} from "./Notification";


const antIcon = <LoadingOutlined style={{ fontSize: 24 }} spin />;
const { Header, Content, Footer, Sider } = Layout;

const { SubMenu } = Menu;


const ExamStateBadge = ({state}) => {
    let regSt;
    let color;
    switch (state) {
        case 'Anmeldung abgeschlossen' : regSt = 'success'; color = "green"; break;
        case 'Anmeldung steht aus' : regSt = 'processing'; color = "pink"; break;
        case 'Anmeldung läuft' : regSt = 'processing'; color = "green"; break;
        default : regSt = 'default';
    }
    return <Badge status = {regSt} text = {state} color = {color}/>;
   }

const examColumns = [
    {
        title: 'Id',
        dataIndex: 'id',
        key: 'id',
    },
    {
        title: 'Prüfungsdatum',
        dataIndex: "examDate",
        key: 'examDate'

    },
    {
        title: 'Prüfung',
        dataIndex: 'examName',
        key: 'examName'
    },
    {
        title: 'Anmeldung',
        dataIndex: 'status',
        key: 'state',
        render: state => <ExamStateBadge state = {state}/>
    },
    {
        title: 'Niveau',
        dataIndex: 'examLevel',
        key: 'examLevel',

    },
    {
        title: 'Prüfungstyp',
        dataIndex: 'examType',
        key: 'examType',
        render: examType => (
            <>
                {
                    examType.map(key=>{
                        let etype = key.typeName;
                        let color;
                        switch (etype){

                                case 'Wirtschaft': case 'Business' : color = 'volcano'; break;
                                case 'Jugendliche':case 'Teenagers' : color = 'yellow'; break;
                                case 'Erwachsene': case 'Adults' : color = 'green'; break;
                                case 'Standard' : case 'common' : color = 'geekblue'; break;
                                default : color = 'grey';
                            }
                            return (
                                <Tag color={color} key={etype}>
                                    {etype.toUpperCase()}
                                </Tag>
                            );
                    })
                }

            </>
        )

    },
    {
        title: 'Action',
        dataIndex: 'operation',
        render: ()=> {
        }
    }
];


function App() {
    const [exams, setExams] = useState([])
    const [collapsed, setCollapsed] = useState(false)
  //  const [filtered, setFiltered] = useState(false)
    const [fetching, setFetching] = useState(true)
    const [theme, setTheme] = useState("dark")
    const [switchColor, setSwitchColor] = useState("#bfbfbf")
    const [showDrawer, setShowDrawer] = useState(false)


    const fetchAllExams = () => getAllExams()
        .then(res=> res.json())
        .then((data)=> {
            console.log(data)
            setExams(data)
            setFetching(false)
        })
        .catch(err=> {
                console.log(err)
                errorNotification(`Ein Fehler ist aufgetreten`, `${err.message} ${err.stack}`,10, 'bottomLeft')

        } )/*.finally(()=> setFetching(false))*/


    const changeTheme = value => {
        setTheme( value ? 'light' : 'dark')
        changeSwitchText()

    }
    const changeSwitchText = () => {
               setSwitchColor(theme==='light' ? "#bfbfbf" : "black")
    }

    const changeShowDrawer = () => {
        setShowDrawer(!showDrawer)
    }

    const expandedExamRowRender = exam => {

        return <ExpandableExamColumns examSource={exam} fetchExams={fetchAllExams}/>;
    }


    useEffect(()=>{
        console.log(`component mounted! Be aware: If we delete arg "deps", the function will be executed permanently!`)
        fetchAllExams()},[]);

    const renderExams = () => {
        if (fetching){
            return <Spin indicator={antIcon}/>
        }
        if(exams.length<=0) return<>
            <ExamDrawerForm
                showDrawer={showDrawer}
                setShowDrawer={setShowDrawer}
                fetchExams={fetchAllExams}
            />
        <Empty>
            <Button
                onClick = {changeShowDrawer}
                type="primary" icon={<PlusOutlined />} size={"medium"}>
                neue Prüfung
            </Button>
        </Empty>
        </>;

        return <>
            <ExamDrawerForm
                showDrawer={showDrawer}
                setShowDrawer={setShowDrawer}
                fetchExams={fetchAllExams}
            />
            <Table
            dataSource={exams}
            columns={examColumns}
            expandable={{expandedRowRender:exam => <>{expandedExamRowRender(exam)}</>}}
            bordered
            title={()=>
                <>
                    <Tag>Prüfungen insgesamt: {exams.length}</Tag>
                    <br/>
                    <br/>
                    <Button
                    onClick = {changeShowDrawer}
                    type="primary" icon={<PlusOutlined />} size={"medium"}>
                    neue Prüfung
                    </Button>
                </>}
            pagination={{pageSize:10}}
            //scroll={{y:600}}

            rowKey={(exam)=>exam.id}/>
        </>
    }


  return (
      <Layout style={{ minHeight: '100vh' }}>
          <Sider collapsible collapsed={collapsed} onCollapse={setCollapsed} theme = {theme}>
              <h5 style={{fontFamily: 'Segoe UI Emoji',fontSize:"inherit", paddingLeft: 20, paddingTop: 5, color:`${switchColor}`}}>
                  <Switch theme={theme} onChange={changeTheme}/> Theme ändern</h5>
              <div className="logo" />
              <Menu theme={theme} defaultSelectedKeys={['1']} mode="inline">
                  <Menu.Item key="1" icon={<PieChartOutlined />}>
                      Statistiken
                  </Menu.Item>
                  <Menu.Item key="10" icon={<EditOutlined />}>
                      Prüfung planen
                  </Menu.Item>
                  <Menu.Item key="2" icon={<DesktopOutlined />}>
                      Aktuelle Prüfungen
                  </Menu.Item>
                  <SubMenu key="sub1" icon={<UserOutlined />} title="Prüfungen">
                      <Menu.Item key="3">anstehend</Menu.Item>
                      <Menu.Item key="4">vergangen</Menu.Item>
                      <Menu.Item key="5">eingeplant</Menu.Item>
                  </SubMenu>
                  <SubMenu key="sub2" icon={<TeamOutlined />} title="Prüfer*innen">
                      <Menu.Item key="6">aktuell</Menu.Item>
                      <Menu.Item key="8">sonstig</Menu.Item>
                  </SubMenu>
                  <Menu.Item key="9" icon={<FileOutlined />}>
                      Files
                  </Menu.Item>
              </Menu>
          </Sider>
          <Layout className="site-layout">
              <Header className="site-layout-background" style={{ padding: 0 }} />
              <Content style={{ margin: '0 16px' }}>
                  <Breadcrumb style={{ margin: '16px 0' }}>
                      <Breadcrumb.Item>Prüfungen</Breadcrumb.Item>
                      <Breadcrumb.Item>anstehend</Breadcrumb.Item>
                  </Breadcrumb>
                  <div className="site-layout-background" style={{ padding: 24, minHeight: 360 }}>
                      {renderExams()}
                  </div>
              </Content>
              <Footer style={{ textAlign: 'center' }}>©2022 Created by Dmitrii Konnov

              </Footer>
          </Layout>

      </Layout>
  );
}

export default App;
