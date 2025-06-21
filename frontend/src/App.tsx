import { useEffect, useState } from 'react'
import './App.css'

export default function App() {
  const [message, setMessage] = useState("");
  const apiUrl = import.meta.env.VITE_API_URL;

  console.log(`SUPOSTA VARIÁVEL: ${apiUrl}`);

  useEffect(() => {
    fetch(`${apiUrl}/api/teste`)
      .then((response) => response.text())
      .then((data) => setMessage(data))
      .catch((error) => {
        console.error("Erro ao chamar API: ", error);
        setMessage("Erro ao chamar API");
      })
  }, [apiUrl])

  return (
    <div className='flex min-h-svh flex-col items-center justify-center'>
      <h1>Mensagem do Back-End</h1>
      <p>{message}</p>
    </div>
  )
}

