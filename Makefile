

test:
	./gradlew clean
	./gradlew assembleDebug --profile
	./gradlew --stop