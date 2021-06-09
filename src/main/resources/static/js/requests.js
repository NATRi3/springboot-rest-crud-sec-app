function sendGet(url) {
    return fetch(url, {
        method: 'GET',
        headers: {'Content-Type': 'application/json'}
    })
        .then(response => response.json())
}

// Просто POST-запрос
function sendPost(url, data) {
    return fetch(url, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    })
}

// Просто PUT-запрос
function sendPut(url, data) {
    return fetch(url, {
        method: 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    })
}

// Просто DELETE-запрос
function sendDelete(url) {
    return fetch(url, {
        method: 'DELETE',
        headers: {'Content-Type': 'application/json'}
    })
}