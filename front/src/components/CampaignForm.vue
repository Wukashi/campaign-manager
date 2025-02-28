<template>
  <div class="modal">
    <h2 class="text-xl font-semibold mb-4">{{ form.id ? "Edytuj kampanię" : "Dodaj nową kampanię" }}</h2>
    <form @submit.prevent="save" class="space-y-4">
      <div>
        <label class="block text-sm font-medium">Nazwa:</label>
        <input v-model="form.name" required class="input-field" />
      </div>

      <div>
        <label class="block text-sm font-medium">Budżet:</label>
        <input type="number" v-model="form.campaignFund" required class="input-field" />
      </div>

      <div>
        <label class="block text-sm font-medium">Stawka (Bid Amount):</label>
        <input type="number" v-model="form.bidAmount" required class="input-field" />
      </div>

      <div>
        <label class="block text-sm font-medium">Słowa kluczowe:</label>
        <input v-model="form.keywords" required class="input-field" />
      </div>

      <div>
        <label class="block text-sm font-medium">Miasto:</label>
        <input v-model="form.town" required class="input-field" />
      </div>

      <div>
        <label class="block text-sm font-medium">Promień (radius):</label>
        <input type="number" v-model="form.radius" min="1" required class="input-field" />
      </div>

      <div>
        <label class="block text-sm font-medium">Status:</label>
        <select v-model="form.status" required class="input-field">
          <option :value="true">Aktywna</option>
          <option :value="false">Nieaktywna</option>
        </select>
      </div>

      <div class="flex space-x-2">
        <button type="submit" class="btn-primary">Zapisz</button>
        <button type="button" @click="$emit('cancel')" class="btn-secondary">Anuluj</button>
      </div>
    </form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  props: {
    campaignData: Object,
    productId: String,
  },
  data() {
    return {
      form: { name: "", campaignFund: null, bidAmount: null, keywords: "", town: "", radius: 1, status: true },
    };
  },
  watch: {
    campaignData: {
      immediate: true,
      handler(newVal) {
        this.form = newVal?.id ? { ...newVal } : { name: "", campaignFund: null, bidAmount: null, keywords: "", town: "", radius: 1, status: true };
      },
    },
  },
  methods: {
    async save() {
      try {
        let response;
        if (this.form.id) {
          response = await axios.put(`http://localhost:8080/campaigns/${this.form.id}`, this.form);
        } else {
          response = await axios.post("http://localhost:8080/campaigns", { ...this.form, productId: this.productId });
        }
        this.$emit("save", response.data);
      } catch (error) {
        console.error("Błąd przy zapisie kampanii:", error.response ? error.response.data : error);
      }
    },
  },
};
</script>
