const requestAdminURL = 'http://localhost:8080/admin/';

const requestUserUrl = 'http://localhost:8080/user/'
// html-блок, локальная таблица всех пользователей
let usersTable = '';

// html-блок, локальная таблица текущего пользователя (самого себя)
let singleUserTable = '';

// html-блок из option-ов, все возможные роли с сервера
let roleOptions = '';

// json object, все возможные роли с сервера
let roles;

let errorModalMessage = '';

// Админ панель: модальное окно удаления пользователя - список ролей юзера
function rolesToString(roles) {
    let rolesAsString = '';
    roles.forEach((singleRole) => {
        rolesAsString += singleRole.name + " ";
    })
    return rolesAsString;
}

// Админ панель: модальное окно создания пользователя
function newUserModal() {
    $("#newUserModal").modal('show')
    let roleSelector = document.getElementById("newModalRoleSelector")
    roleSelector.innerHTML = roleOptions
}

// Создаём пользователя
function newUser() {
    let rolesArray = []
    let allOptions = document.getElementById("newModalRoleSelector").options
    let body = {}
    for (let i = 0; i < allOptions.length; i++) {
        if (allOptions[i].selected) {
            rolesArray.push({
                id: allOptions[i].id,
                name: allOptions[i].value
            })
        }
    }
    body = {
        firstName: $("#newUserFirstName").val(),
        lastName: $("#newUserLastName").val(),
        age: $("#newUserAge").val(),
        username: $("#newUserEmail").val(),
        password: $("#newUserPassword").val(),
        roles: rolesArray
    }
    let isok = true;
    sendPost(requestAdminURL, body)
        .then(res => {
            if(res.ok){
                document.getElementById("allUsersTableFetch").innerHTML = ''
                onStartup()
                clearNewUserForms()
            } else {
                isok = false;
                return res.json()
            }
        })
        .then(function (res) {
            if (isok){
                document.getElementById("ModalTitle").innerText = "Success"
                document.getElementById("messageModalText").innerText = "User successful created"
                $('#messageModal').modal('toggle')
            } else {
                document.getElementById("ModalTitle").innerText = "Incorrect values"
                document.getElementById("messageModalText").innerText = createMessageText(res.errors)
                $('#messageModal').modal('toggle')
            }
        })

}

// Чистим поля модальное окна создания пользователя
function clearNewUserForms() {
    $("#newUserFirstName").val("");
    $("#newUserLastName").val("");
    $("#newUserAge").val("");
    $("#newUserEmail").val("");
    $("#newUserPassword").val("");
}

// Админ панель: модальное окно редактирования пользователя
function editUserModal(userId) {
    $("#editUserModal").modal('show');
    sendGet(requestAdminURL + userId)
        .then(response => {
                $("#editId").val(response.id)
                $("#editFirstName").val(response.firstName)
                $("#editLastName").val(response.lastName)
                $("#editAge").val(response.age)
                $("#editEmail").val(response.username)
                $("#editPassword").val('')
                let roleSelector = document.getElementById("editModalRoleSelector")
                roleSelector.innerHTML = roleOptions
            }
        )
}

function updateUserPassword(){
    let body = {}
    body = {
        id: $("#editId").val(),
        password:   $("#editPassword").val(),
    }
    let isok = true;
    sendPut(requestAdminURL + "pass", body)
        .then(res => {
            if(!res.ok){
                isok = false;
                return res.json()
            }
        })
        .then(function (res) {
            $("#editUserModal").modal('hide')
            if (!isok){
                document.getElementById("ModalTitle").innerText = "Incorrect values"
                document.getElementById("messageModalText").innerText = createMessageText(res.errors)
                $('#messageModal').modal('toggle')
            }
        })
}

// Редактируем пользователя
function editUser() {
    let rolesArray = []
    let body = {}
    let allOptions = document.getElementById("editModalRoleSelector").options
    for (let i = 0; i < allOptions.length; i++) {
        if (allOptions[i].selected) {
            rolesArray.push({
                id: allOptions[i].id,
                name: allOptions[i].value
            })
        }
    }
    body = {
        id: $("#editId").val(),
        firstName:  $("#editFirstName").val(),
        lastName:   $("#editLastName").val(),
        age:        $("#editAge").val(),
        username:   $("#editEmail").val(),
        roles: rolesArray
    }
        let isok = true;
        sendPut(requestAdminURL, body)
            .then(res => {
                if(res.ok){
                    let userId = parseInt(document.getElementById("editId").value)
                    $('#tr' + userId).replaceWith(jsonToRow(body))
                } else {
                    isok = false;
                    return res.json()
                }
            })
            .then(function (res) {
                $("#editUserModal").modal('hide')
                if (!isok){
                    document.getElementById("ModalTitle").innerText = "Incorrect values"
                    document.getElementById("messageModalText").innerText = createMessageText(res.errors)
                    $('#messageModal').modal('toggle')
                }
            })
}

// Удаляем пользователя
function deleteUser() {
    const idOfDeletedUser = $("#deleteId").val();
    sendDelete(requestAdminURL + idOfDeletedUser)
        .then(() => {
            let deletedUserId = parseInt(document.getElementById("deleteId").value)
            $('#tr' + deletedUserId).remove()
        })
        .then($("#deleteUserModal").modal('hide'))
}

// Админ панель: модальное окно удаления пользователя
function deleteUserModal(userId) {
    $("#deleteUserModal").modal('show');
    sendGet(requestAdminURL + userId)
        .then(response => {
            $("#deleteId").val(response.id),
                $("#deleteFirstName").val(response.firstName),
                $("#deleteLastName").val(response.lastName),
                $("#deleteAge").val(response.age),
                $("#deleteEmail").val(response.username),
                $("#deletePassword").val(response.password),
                $("#deleteRoles").val(rolesToString(response.roles))
        })
}

onStartup()