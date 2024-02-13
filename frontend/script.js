function showSideBar() {
    const sidebar = document.querySelector('.hamburger');
    sidebar.style.display = 'flex';
}

function hideSideBar() {
    const sidebar = document.querySelector('.hamburger');
    sidebar.style.display = 'none';
}

const Orders = {
    handleHttpNotOk: function () {
        throw new Error('Network response was not ok')
    },
    handleError: function(err) {
        console.error('Error:', err)
        let i = 0
        while(i < 4) {
            if ('content' in document.createElement('template')) {
                const trendingBody = document.querySelector('#trending')
                const template = document.querySelector('#trending-product')
                const clone = template.content.cloneNode(true)
                let tp = clone.querySelectorAll('div')
                tp[0].querySelector('img').src = './assets/images/trending-product.webp'
                tp[1].textContent = 'Placeholder'
                tp[2].textContent = '£XX.XX'
                trendingBody.appendChild(clone)
            }
            i++
        }
    },
    fetchProducts: function (url, callback) {
        fetch(url).then((response) => {
            if (!response.ok) {
                this.handleHttpNotOk()
            }
            return response.json()
        }).then((json) => {
            json.data.forEach((item) => callback(item))
        }).catch((err) => this.handleError(err))
}

Orders.fetchProducts('http://localhost:8080/products/', appendProduct)

function appendProduct(product) {
    let htmlTemplate = `<template id="product">
  <tr>
    <td class="record"></td>
    <td></td>
  </tr>
</template>`
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
        data.data.forEach((element) => {
            if ('content' in document.createElement('template')) {
                const trendingBody = document.querySelector('#trending');
                const template = document.querySelector('#trending-product');
                const clone = template.content.cloneNode(true);
                let tp = clone.querySelectorAll('div');
                const img = tp[0].querySelector('img');
                img.addEventListener('error', function (event) {
                    event.target.src = './assets/images/trending-product.webp';
                    event.onerror = null;
                });
                tp[1].textContent = element.name;
                tp[2].textContent = '£' + element.unitPrice;
                trendingBody.appendChild(clone);
            } else {
                tp[0].textContent = 'Placeholder';
                tp[1].textContent = '£XX.XX';
            }
        });
    })
    .catch((error) => {
        console.error('Error:', error);

        for (let i = 0; i < 4; i++) {
            if ('content' in document.createElement('template')) {
                const trendingBody = document.querySelector('#trending');
                const template = document.querySelector('#trending-product');
                const clone = template.content.cloneNode(true);
                let tp = clone.querySelectorAll('div');
                tp[0].querySelector('img').src = './assets/images/trending-product.webp';
                tp[1].textContent = 'Placeholder';
                tp[2].textContent = '£XX.XX';
                trendingBody.appendChild(clone);
            }
        }
    });



