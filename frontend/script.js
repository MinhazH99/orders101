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
            var divWithProductName = document.createElement('div');
            productName = document.createTextNode(element.name);
            divWithProductName.appendChild(productName);
            var divWithUnitPrice = document.createElement('div');
            unitPrice = document.createTextNode(element.unitPrice);
            divWithUnitPrice.append(unitPrice);
            anchor.appendChild(divWithProductName);
            anchor.appendChild(divWithUnitPrice);

            const trendingProduct = document.getElementById('trending');
            trendingProduct.appendChild(anchor);
        });
    })
    .catch((error) => {
        console.error('Error:', error);

        for (let i = 0; i < 4; i++) {
            var anchor = document.createElement('a');
            var divWithImage = document.createElement('div');
            var productImage = document.createElement('img');
            productImage.src = './assets/images/trending-product.webp';
            productImage.className = 'site-image';
            divWithImage.appendChild(productImage);
            var divWithProductName = document.createElement('div');
            var productName = document.createTextNode('Placeholder');
            divWithProductName.appendChild(productName);
            anchor.appendChild(divWithProductName);
            var divWithUnitPrice = document.createElement('div');
            var unitPrice = document.createTextNode('£XX.XX');
            divWithUnitPrice.append(unitPrice);
            anchor.appendChild(divWithImage);
            anchor.appendChild(divWithProductName);
            anchor.appendChild(divWithUnitPrice);
            const trendingProduct = document.getElementById('trending');
            trendingProduct.appendChild(anchor);
        }
    });
