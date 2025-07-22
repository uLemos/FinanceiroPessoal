// monitoring-proxy.js
const express = require('express');
const axios = require('axios');
require('dotenv').config();

const app = express();
const PORT = process.env.PORT;

async function getJwtToken() {
  const response = await axios.post(
    'https://financeiropessoal-production.up.railway.app/v1/api/auth/login', 
    {
      email: process.env.AUTH_EMAIL,
      senha: process.env.AUTH_PASSWORD,
    }
  );
  return response.data.token;
}

app.get('/proxy/prometheus', async (req, res) => {
  try {
    const jwt = await getJwtToken();

    const prometheusRes = await axios.get(
      'https://financeiropessoal-production.up.railway.app/actuator/prometheus',
      {
        headers: {
          Authorization: `Bearer ${jwt}`,
        },
      }
    );

    res.set('Content-Type', 'text/plain');
    res.send(prometheusRes.data);
  } catch (err) {
    console.error('Erro no proxy:', err.message);
    res.status(500).send('Erro ao buscar métricas.');
  }
});

app.listen(PORT, () => {
  console.log(`✅ Proxy de monitoramento rodando na porta ${PORT}`);
});
