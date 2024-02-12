function showSideBar() {
    const sidebar = document.querySelector('.hamburger');
    sidebar.style.display = 'flex';
}

function hideSideBar() {
    const sidebar = document.querySelector('.hamburger');
    sidebar.style.display = 'none';
}

// Specify the API endpoint for user data
const apiUrl = 'http://localhost:8080/products/';

// Make a GET request using the Fetch API
fetch(apiUrl)
    .then((response) => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then((data) => {
        // Process the retrieved user data
        data.data.forEach((element) => {
            console.log(element);
            var anchor = document.createElement('a');
            var div = document.createElement('div');
            productName = document.createTextNode(element.name);
            div.appendChild(productName);
            anchor.appendChild(div);

            const trendingProduct = document.getElementById('test');
            trendingProduct.appendChild(anchor);
        });
    })
    .catch((error) => {
        console.error('Error:', error);
    });
