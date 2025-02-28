<template>
  <div>
    <h1>Lista Produktów</h1>
    <button @click="openAddForm">Dodaj nowy produkt</button>

    <ProductForm
      v-if="showForm"
      :productData="selectedProduct"
      @save="addOrUpdateProduct"
      @cancel="showForm = false"
    />

    <p v-if="products.length === 0">Brak produktów do wyświetlenia</p>

    <table v-if="products.length > 0">
      <thead>
        <tr>
          <th>ID</th>
          <th>Nazwa</th>
          <th>Kategoria</th>
          <th>Akcje</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="product in products" :key="product.id">
          <td>{{ product.id }}</td>
          <td>{{ product.name }}</td>
          <td>{{ product.category }}</td>
          <td>
            <button @click="editProduct(product)">Edytuj</button>
            <button @click="deleteProduct(product.id)">Usuń</button>
            <button @click="viewCampaigns(product.id)">Kampanie</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import axios from 'axios';
import { useRouter } from 'vue-router';
import ProductForm from './ProductForm.vue';

export default {
  components: { ProductForm },
  data() {
    return {
      products: [],
      showForm: false,
      selectedProduct: {},
    };
  },
  setup() {
    const router = useRouter();

    viewCampaigns(id) {
      this.$router.push({ name: 'campaigns', params: { productId: id } });
    }

    return { viewCampaigns };
  },
  methods: {
    async fetchProducts() {
      try {
        const response = await axios.get('http://localhost:8080/products');
        if (Array.isArray(response.data)) {
          this.products = [...response.data];
        }
      } catch (error) {
        console.error('Błąd podczas pobierania produktów:', error);
      }
    },

    async deleteProduct(id) {
      if (confirm('Czy na pewno chcesz usunąć ten produkt?')) {
        try {
          await axios.delete(`http://localhost:8080/products/${id}`);
          this.products = this.products.filter(product => product.id !== id);
        } catch (error) {
          console.error('Błąd podczas usuwania produktu:', error);
        }
      }
    },

    editProduct(product) {
      this.selectedProduct = { ...product };
      this.showForm = true;
    },

    openAddForm() {
      this.selectedProduct = {};
      this.showForm = true;
    },

    addOrUpdateProduct(newProduct) {
      const index = this.products.findIndex(p => p.id === newProduct.id);
      if (index !== -1) {
        this.products[index] = newProduct;
      } else {
        this.products = [...this.products, newProduct];
      }
      this.showForm = false;
    },
  },
  mounted() {
    this.fetchProducts();
  },
};
</script>
