import fetch from 'unfetch'

const checkStatus = response => {
    if(response.ok) {
        return response;
    }
    const error = new Error(response.statusText)
    error.response = response
    return Promise.reject(error)
}

export const getAllExams = () =>
    fetch("/api/v1/exams")
        .then(checkStatus);

export const addNewExam = exam =>
    fetch("/api/v1/exams",{
        headers: {'Content-Type':'application/json'},
        method: 'POST',
        body: JSON.stringify(exam)
    }).then(checkStatus);

export const updateExam = (exam) =>
    fetch("/api/v1/exams",{
        headers: {'Content-Type':'application/json'},
        method: 'PUT',
        body: JSON.stringify(exam)
    }).then(checkStatus)

export const deleteExam = id =>
    fetch('api/v1/exams/'+id,{
        headers: {'Content-Type':'application/json'},
        method: 'DELETE'
    }).then(checkStatus);

export const getAvailableExamTypes = ()=>
    fetch("/api/v1/examtypes")
        .then(checkStatus);
