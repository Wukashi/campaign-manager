<template>
  <div>
    <h2>Kampanie dla produktu {{ productId }}</h2>
    <button @click="openCampaignForm()">Dodaj Kampanię</button>

    <CampaignForm
      :campaignData="selectedCampaign"
      :productId="productId"
      @save="fetchCampaigns"
      @cancel="selectedCampaign = null"
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
            <button @click="openCampaignForm(campaign)">Edytuj</button>
            <button @click="deleteCampaign(campaign.id)">Usuń</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import axios from "axios";
import CampaignForm from "@/components/CampaignForm.vue";

export default {
  components: { CampaignForm },
  props: ["productId"], // ✅ Odbieramy productId z routera!
  data() {
    return {
      campaigns: [],
      showForm: false,
      selectedCampaign: {},
    };
  },
  methods: {
    async fetchCampaigns() {
        try {
          const response = await axios.get(`http://localhost:8080/products/${this.productId}/campaigns`);
          this.campaigns = response.data;
        } catch (error) {
          console.error("Błąd ładowania kampanii:", error);
        }
      },
    openCampaignForm(campaign = null) {
      this.selectedCampaign = campaign || {};
      this.showForm = true;
    },
    async deleteCampaign(id) {
      if (confirm("Czy na pewno chcesz usunąć tę kampanię?")) {
        try {
          await axios.delete(`http://localhost:8080/campaigns/${id}`);
          this.campaigns = this.campaigns.filter(c => c.id !== id);
        } catch (error) {
          console.error("Błąd przy usuwaniu kampanii:", error);
        }
      }
    },
    handleCampaignSave(newCampaign) {
      const index = this.campaigns.findIndex(c => c.id === newCampaign.id);
      if (index !== -1) {
        this.campaigns[index] = newCampaign;
      } else {
        this.campaigns.push(newCampaign);
      }
      this.showForm = false;
    },
  },
  watch: {
    productId: {
      immediate: true,
      handler() {
        this.fetchCampaigns();
      }
    }
  }
};
</script>
