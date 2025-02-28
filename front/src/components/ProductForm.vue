<template>
  <div class="modal">
    <h2>{{ productData?.id ? 'Edytuj produkt' : 'Dodaj nowy produkt' }}</h2>
    <form @submit.prevent="save">
      <label>Nazwa:</label>
      <input v-model="form.name" required />

      <label>Kategoria:</label>
      <input v-model="form.category" required />

      <button type="submit">Zapisz</button>
      <button type="button" @click="$emit('cancel')">Anuluj</button>
    </form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  props: {
    productData: {
      type: Object,
      default: () => ({})
    }
  },
  data() {
    return {
      form: { name: '', category: '' }
    };
  },
  watch: {
    productData: {
      immediate: true,
      handler(newVal) {
        if (newVal && Object.keys(newVal).length > 0) {
          this.form = { ...newVal };
        } else {
          this.form = { name: '', category: '' };
        }
      }
    }
  },
  methods: {
    async save() {
      try {
        let response;
        if (this.productData?.id) {
          response = await axios.put(`http://localhost:8080/products/${this.productData.id}`, this.form);
        } else {
          response = await axios.post('http://localhost:8080/products', this.form);
        }
        this.$emit('save', response.data);
      } catch (error) {
        console.error('Błąd przy zapisie:', error);
      }
    }
  }
};
</script>

<style scoped>
.modal {
  padding: 20px;
  background: white;
  border: 1px solid #ddd;
}
</style>
