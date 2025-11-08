
# Catálogo técnico de **virus informáticos**  
**Autor/a:** _Mario Asenjo_ · **Fecha:** 5 de noviembre de 2025  

---

## Índice rápido
1. [Qué es (y qué no es) un virus](#que-es)
2. [Criterios de relevancia/impacto usados](#criterios)
3. [Tipos de virus — de mayor a menor impacto](#tipos)
   - Polimórficos
   - Metamórficos
   - Macro (Office/Docs)
   - De correo (Email-borne)
   - De sector de arranque (Boot sector)
   - Infectores de archivos (File infectors)
   - Multipartitos
   - Script/web (HTML/JS)
   - Residentes vs no residentes
   - Companion/Link
   - Overwrite/Truncadores
4. [Mapa mental rápido](#mapa)
5. [IOC/Detección: checklist exprés](#deteccion)
6. [Glosario mínimo para quedar pro](#glosario)
7. [Notas de autoría y metodología](#autoria)

---

## <a name="que-es"></a>1) Qué es (y qué no es) un virus

- **Virus:** código que **se adjunta** a otro ejecutable/documento y **requiere intervención** o ejecución del host para propagarse. Se **replica** insertándose en otros archivos o áreas del sistema.
- **No confundir con:**
  - **Gusano (worm):** se propaga **autónomamente** por red sin “huésped” (ej.: WannaCry — gusano, no virus).
  - **Troyano:** no se replica; engaña al usuario para instalarse.
  - **Ransomware:** _payload_ (función), no tipo de propagación. Puede viajar dentro de un virus, troyano o gusano.

> En este documento listado **solo de virus**; si menciono payloads (ransom, spyware, etc.), es para contexto.

---

## <a name="criterios"></a>2) Cómo ordené la relevancia/impacto

- **Superficie de ataque actual** (probabilidad de encuentro hoy).
- **Daño potencial** (confidencialidad/integridad/disponibilidad, coste de recuperación).
- **Velocidad de propagación** (en ecosistemas modernos).
- **Evasión/Stealth** (dificultad de detección).

---

## <a name="tipos"></a>3) Tipos de virus (ordenados por impacto práctico)

### 3.1 Polimórficos (Top impacto)
**Idea:** cada copia **cifra/obfusca** su cuerpo y **cambia** la rutina de descifrado, generando múltiples variantes con la **misma** funcionalidad.  
**Por qué duelen:** rompen firmas estáticas, elevan el coste de respuesta.  
**Vectores típicos:** ejecutables PE/ELF adjuntos a instaladores “crack”, _droppers_.  
**Payload frecuente:** _downloaders_, _stealers_, _wipers_.  
**Señales:** alta entropía en secciones, descifradores cortos variables, APIs de criptografía embebidas.  
**Mitigación:** análisis estático+dinámico, **YARA** con rasgos de comportamiento (no cadenas fijas), sandbox con tracing API.

---

### 3.2 Metamórficos (Aún más sigilosos, pero menos comunes)
**Idea:** se **reescriben a sí mismos** en cada generación (mutación de código real, no solo cifrado).  
**Impacto:** muy difíciles de firmar; más raros por su complejidad.  
**Señales:** reordenación de bloques, inserción de instrucciones basura, cambios de flujo.  
**Mitigación:** desensamblado + normalización de flujo, detección semántica, ML de comportamiento.

---

### 3.3 Macro (Office/Docs)
**Idea:** macros (VBA/VBScript/Office JS) incrustadas en documentos que infectan **plantillas** (p. ej. `Normal.dotm`) para propagarse.  
**Impacto:** enorme en entornos corporativos por ingeniería social.  
**Señales:** documentos con **auto-open**/autoexec, llamadas a `WScript.Shell`, descargas HTTP, escritura a rutas de inicio.  
**Mitigación:** macros firmadas, Modo protegido, bloqueo por GPO, deshabilitar OLE, abrir en vista protegida.

---

### 3.4 De correo (Email-borne)
**Idea:** adjuntos infectados que se **reenvían** leyendo la libreta de direcciones del cliente de correo.  
**Impacto:** latigazo inicial en organizaciones, sobre todo con usuarios locales/legacy.  
**Señales:** picos de envío saliente, plantillas de asunto repetidas, uso de MAPI/Outlook interop.  
**Mitigación:** _sandboxes_ de adjuntos, desactivación de ejecución automática, filtros de contenido, DMARC/SPF/DKIM.

---

### 3.5 De sector de arranque / MBR/Bootkit
**Idea:** infectan **MBR/boot** o cadena de arranque UEFI, cargándose **antes** del SO.  
**Impacto:** **alta persistencia** y evasión, potencial para _wipers_.  
**Señales:** hashes anómalos de bootloader, hooks tempranos, particiones ocultas.  
**Mitigación:** **Secure Boot**, TPM/Measured Boot, verificación de integridad del loader, arranque desde medio limpio.

---

### 3.6 Infectores de archivos (File infectors)
**Idea:** se **insertan** en ejecutables (PE/ELF/Mach-O) y, al ejecutarlos, infectan otros binarios.  
**Impacto:** clásico pero vigente en ecosistemas con compartición de binarios.  
**Modalidades:** prepend/append/insert, cavity (rellenos).  
**Señales:** cambios de tamaño/entropía en secciones `.text/.rdata`, imports nuevos sospechosos.  
**Mitigación:** listas blancas, firmas de editor, EDR con tracing de procesos/escrituras.

---

### 3.7 Multipartitos
**Idea:** combinan **múltiples vectores** (p. ej., archivo + boot).  
**Impacto:** resiliencia y supervivencia tras limpiezas parciales.  
**Mitigación:** **higiene completa**: reparar boot + desinfección de archivos + políticas de macros.

---

### 3.8 Script/Web (HTML/JS)
**Idea:** código malicioso en **HTML/JS/VBS** que infecta páginas o repos locales de archivos.  
**Impacto:** medio-alto en portales legacy e intranets.  
**Señales:** iframes ocultos, _obfuscators_, evals dinámicos, inyección en CMS.  
**Mitigación:** CSP estrictas, SRI, revisión de integridad, escáneres de contenido.

---

### 3.9 Residentes vs No residentes
- **Residentes:** se cargan en memoria y **interceptan** llamadas (hooks) para infectar en caliente.  
- **No residentes:** infectan durante la ejecución y terminan.  
**Impacto:** los **residentes** dificultan la limpieza (requiere _offline/WinPE_).

---

### 3.10 Companion / Link
**Idea:** crean un ejecutable “compañero” que el sistema ejecuta **antes** que el legítimo (por orden de búsqueda).  
**Impacto:** moderado; útil en entornos con rutas mal gestionadas.  
**Mitigación:** `PATH` minimalista, AppLocker/WDAC, extensiones visibles.

---

### 3.11 Overwrite / Truncadores / Sobreescritores
**Idea:** sobrescriben el contenido del archivo host, **destruyéndolo**.  
**Impacto:** alto en **disponibilidad**, bajo en persistencia (ruidosos).  
**Mitigación:** copias de seguridad versionadas + restauración inmutable.

---

## <a name="mapa"></a>4) Mapa mental rápido (versión 1)

```
Propagación
│
├─ Adjunta a ejecutables
│   ├─ File infectors
│   ├─ Polimórficos
│   └─ Metamórficos
│
├─ Documentos
│   └─ Macro (VBA/Office JS)
│
├─ Arranque
│   └─ Boot/MBR/UEFI
│
└─ Canal humano
    └─ Email-borne / Ingeniería social
```

---

## <a name="deteccion"></a>5) IOC & Detección

- **Cambios de integridad**: hashes de binarios críticos, plantillas de Office (`Normal.dotm`) y bootloader.  
- **Entropía y secciones**: PE con secciones nuevas/entropía alta anómala.  
- **Autoexec**: macros con `AutoOpen`, `Document_Open`, o llamadas a `WScript.Shell`.
- **Actividad de correo**: picos de envío, uso de MAPI/Outlook Automation por procesos no estándar.  
- **Persistencia**: claves `Run/RunOnce`, tareas programadas recién creadas, _AppInit_DLLs_.  
- **Boot chain**: cambios en NVRAM/EFI, desajuste entre medidas TPM y binarios esperados.  
- **Network**: actividad de _beaconing_ tras abrir documentos o ejecutables “normales”.

> **Tip**: cuando describo IOCs en un informe, anoto tanto el **qué** como el **porqué** afecta al modelo mental de amenaza. Esa frase me ha salvado revisiones.

---

## <a name="glosario"></a>6) Glosario breve

- **Infección**: inserción del virus en un host (archivo/boot/doc).  
- **Payload**: lo que **hace** el virus (robar, cifrar, borrar…), independiente de **cómo** se propaga.  
- **Polimorfismo**: misma lógica, **cuerpo cifrado** y cambiando.  
- **Metamorfismo**: **reescritura** real del código en cada generación.  
- **Residente**: permanece en memoria; **no residente**: actúa y termina.  
- **Multipartito**: más de un vector de infección.

---

## <a name="autoria"></a>7) Notas de autoría y metodología

- **Criterios claros:** ordené por **superficie real + daño + evasión**, no por moda.  
- **Separé vector vs payload:** para evitar confundir **ransomware** (función) con **virus** (mecanismo).  
- **Incluí señales prácticas:** qué ver en **logs, binarios y plantillas** (lo que realmente reviso).  
- **Recomendación de saneamiento:** siempre **offline first** para bootkits/virus residentes y **restauración inmutable** para overwrite.  
- **Estilo de reporte:** encabezados concisos, bullets accionables y glosario propio — consistente con mis otros informes.

---

### Créditos y versión
Este documento lo he redactado íntegrammente, apoyándome en mi experiencia y criterios de evaluación, pudiendo estar incorrecto en cualquier punto.
Agradecería una revisión para cambiar lo que fuera conveniente.
**Versión:** 1.0 · **Última edición:** 5/11/2025
