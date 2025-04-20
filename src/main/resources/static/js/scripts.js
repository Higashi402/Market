function applyFilters() {
    const selectedDeliveryTime = document.querySelector('input[name="deliveryTime"]:checked').value;

    const filter = {
        minPrice: document.getElementById('minPrice').value,
        maxPrice: document.getElementById('maxPrice').value,
        deliveryTime: selectedDeliveryTime === "any" ? null : parseInt(selectedDeliveryTime, 10)
    };

    console.log(filter);

    fetch('/catalog/filter', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(filter)
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            updateProductList(data);
        })
        .catch(error => console.error('Ошибка:', error));
}

function updateProductList(products) {
    const catalogSection = document.querySelector('.catalog');
    catalogSection.innerHTML = '';

    if (!Array.isArray(products)) {
        console.error("Ожидался массив товаров, но получено:", products);
        return;
    }

    products.forEach(product => {
        const productCard = `
            <div class="card">
                <a href="/product/${product.articleNumber}">
                    <img src="img/products/${product.image}.jpg" alt="Изображение">
                    <h2>${product.title}</h2>
                </a>
                <span class="price">${product.price} ₽</span>
                <button>Купить</button>
            </div>
        `;
        catalogSection.innerHTML += productCard;
    });
}