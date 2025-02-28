<template>
  <div>
    <h1>Kampanie dla produktu {{ productId }}</h1>
    <button @click="openAddForm">Dodaj nową kampanię</button>

    <CampaignForm
      v-if="showForm"
      :productId="productId"
      :campaignData="selectedCampaign"
      @save="handleCampaignSave"
      @cancel="showForm = false"
    />

    <p v-if="campaigns.length === 0">Brak kampanii do wyświetlenia</p>

    <table v-if="campaigns.length > 0">
      <thead>
        <tr>
          <th>ID</th>
          <th>Nazwa</th>
          <th>Budżet</th>
          <th>Akcje</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="campaign in campaigns" :key="campaign.id">
          <td>{{ campaign.id }}</td>
          <td>{{ campaign.name }}</td>
          <td>{{ campaign.budget }}</td>
          <td>
            <button @click="editCampaign(campaign)">Edytuj</button>
            <button @click="deleteCampaign(campaign.id)">Usuń</button>
          </td>
        </tr>
      </tbody>
    </table>
    <button @click="$router.push('/')">Powrót do produktów</button>
  </div>
</template>

<script>
import axios from 'axios';
import CampaignForm from '@/components/CampaignForm.vue';

export default {
  components: { CampaignForm },
  props: ['productId'],
  data() {
    return {
      campaigns: [],
      showForm: false,
      selectedCampaign: {},
    };
  },
  methods: {
    async fetchCampaigns() {
      console.log('Otrzymane productId:', this.productId); // Sprawdzenie, czy ID jest poprawne
      try {
        const response = await axios.get(`http://localhost:8080/campaigns/product/${this.productId}`);
        if (Array.isArray(response.data)) {
          this.campaigns = [...response.data];
        }
      } catch (error) {
        console.error('Błąd podczas pobierania kampanii:', error);
      }
    },

    async deleteCampaign(id) {
      if (confirm('Czy na pewno chcesz usunąć tę kampanię?')) {
        try {
          await axios.delete(`http://localhost:8080/campaigns/${id}`);
          this.campaigns = this.campaigns.filter(campaign => campaign.id !== id);
        } catch (error) {
          console.error('Błąd podczas usuwania kampanii:', error);
        }
      }
    },

    editCampaign(campaign) {
      this.selectedCampaign = { ...campaign };
      this.showForm = true;
    },

    openAddForm() {
      this.selectedCampaign = {};
      this.showForm = true;
    },

    addOrUpdateCampaign(newCampaign) {
      const index = this.campaigns.findIndex(c => c.id === newCampaign.id);
      if (index !== -1) {
        this.campaigns[index] = newCampaign;
      } else {
        this.campaigns = [...this.campaigns, newCampaign];
      }
      this.showForm = false;
    },
  },
  mounted() {
    this.fetchCampaigns();
  },
};
</script>
