function fetchData(url, callback) {
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

export { fetchData };
