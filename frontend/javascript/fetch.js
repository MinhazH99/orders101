function fetchData(url) {
    return fetch(url)
        .then((response) => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then((json) => json.data)
        .catch((err) => console.error('Error:', err));
}

function postOrder(url, order) {
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(order),
    })
        .then((response) => response.json())
        .then((data) => console.log(data));
}

function patchOrder(url) {
    fetch(url, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then((response) => response.json())
        .then((data) => console.log(data));
}

export { fetchData, postOrder, patchOrder };
