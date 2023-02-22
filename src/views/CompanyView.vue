<template>
    <div>
        <CompanyGenreView :title="title" :apiUrl="url" />
    </div>
</template>

<script>
    import CompanyGenreView from './CompanyGenreView.vue';
    export default {
        name:"CompanyView",
        components:{ CompanyGenreView },
        data() {
            return {
                title : "기획사",
                url:"/api/company"
            }
        }
    }
</script>