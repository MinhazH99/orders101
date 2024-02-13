function showSideBar() {
    const sidebar = document.querySelector('.hamburger')
    sidebar.style.display = 'flex'
}

function hideSideBar() {
    const sidebar = document.querySelector('.hamburger')
    sidebar.style.display = 'none'
}

const Products = {
    handleHttpNotOk: function () {
        throw new Error('Network response was not ok')
    },
    handleError: function(err) {
        console.error('Error:', err)
        let i = 0
        while(i < 4) {
            if ('content' in document.createElement('template')) {
                const trendingDiv = document.querySelector('#trending')
                const templateClone = document.querySelector('#trending-product-template').content.cloneNode(true)
                trendingDiv.appendChild(templateClone)
            }
            i++
        }
    },
    fetchData: function (url, callback) {
        fetch(url).then((response) => {
            if (!response.ok) {
                this.handleHttpNotOk()
            }
            return response.json()
        }).then((json) => {
            json.data.forEach((item) => callback(item))
        }).catch((err) => this.handleError(err))
    }
}

Products.fetchData('http://localhost:8081/products/', appendProduct)

function appendProduct(product) {
    // Test to see if the browser supports the HTML template element by checking
    // for the presence of the template element's content attribute.
    if ('content' in document.createElement('template')) {
        const trendingDiv = document.querySelector('#trending')
        const templateClone = document.querySelector('#trending-product-template').content.cloneNode(true)
        
        let prodImage = templateClone.querySelector('#trending-div-product-image')
        prodImage.setAttribute('src', './assets/images/trending-product.webp')
        
        let labelDiv = templateClone.querySelector('#trending-div-product-label')
        labelDiv.textContent = product.name

        let priceDiv = templateClone.querySelector('#trending-div-product-price')
        priceDiv.textContent = '£' + product.unitPrice

        trendingDiv.appendChild(templateClone)
    } else {
        // perhaps just append html here if templates are not supported?
        // ...
    }  
}



