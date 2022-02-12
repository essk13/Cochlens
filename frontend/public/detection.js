Promise.all([
  faceapi.loadFaceRecognitionModel('/models'),
  faceapi.loadFaceLandmarkModel('/models'),
  faceapi.loadTinyFaceDetectorModel('/models'),
  faceapi.loadFaceExpressionModel('/models')
]).then(console.log('success')).catch(console.log('err'))